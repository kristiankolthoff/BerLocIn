package de.unima.webdataintegration.location.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class LocationXMLReader extends XMLMatchableReader<Location, Attribute> implements
				FusibleFactory<Location, Attribute>{

	private DateTimeFormatter germanFormatter;
	private int fromReadingIgnores;
	private int toReadingIgnores;
	
	public LocationXMLReader() {
		germanFormatter = DateTimeFormatter
				.ofLocalizedTime(FormatStyle.SHORT)
				.withLocale(Locale.GERMAN);
	}

	@Override
	protected void initialiseDataset(DataSet<Location, Attribute> dataset) {
		super.initialiseDataset(dataset);
		dataset.addAttribute(Location.NAME);
		dataset.addAttribute(Location.TYPE);
		dataset.addAttribute(Location.EMAIL);
		dataset.addAttribute(Location.WEBSITE);
		dataset.addAttribute(Location.PHONE);
		dataset.addAttribute(Location.STREET_ADDRESS);
		dataset.addAttribute(Location.POSTAL_CODE);
		dataset.addAttribute(Location.LATITUDE);
		dataset.addAttribute(Location.LONGITUDE);
		dataset.addAttribute(Location.OPENING_HOURS);
		dataset.addAttribute(Location.RATING);
		dataset.addAttribute(Location.REVIEW_COUNT);
		dataset.addAttribute(Location.PRICE);
		dataset.addAttribute(Location.PHOTO_URLS);
		dataset.addAttribute(Location.REVIEWS);
	}

	@Override
	public Location createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		Location location = new Location(id, provenanceInfo);
		location.setName(getValueFromChildElement(node, "name"));
		location.setType(getValueFromChildElement(node, "type"));
		
		//Parse contact information
		location.setEmail(getValueFromChildElement(node, "email"));
		location.setWebsite(getValueFromChildElement(node, "website"));
		location.setPhone(getValueFromChildElement(node, "phone"));
		
		//Parse address information
		location.setStreetAddress(getValueFromChildElement(node, "streetAddress"));
		location.setPostalCode(getValueFromChildElement(node, "postalCode"));
		String latiude = getValueFromChildElement(node, "latitude");
		double latiudeParsed = Objects.nonNull(latiude) ? Double.parseDouble(latiude) : 0d;
		String longitude = getValueFromChildElement(node, "longitude");
		double longitudeParsed = Objects.nonNull(longitude) ? Double.parseDouble(longitude) : 0d;
		location.setLatitude(latiudeParsed);
		location.setLongitude(longitudeParsed);
		
		//Parse opening hours
		for (int i = 0; i < Location.WEEKDAYS.size(); i++) {
			location.addOpeningHours(extractOpeningHours(node, Location.WEEKDAYS.get(i)));
		}
		
		//Parse photo urls
		List<String> photoUrls = extractPhotoUrls(node);
		location.setPhotoUrls(photoUrls);
		
		//Parse rating and review_count
		String rating = getValueFromChildElement(node, "rating");
		double parsedRating = Objects.nonNull(rating) ? Double.parseDouble(rating) : 0d;
		String reviewCount = getValueFromChildElement(node, "reviewCount");
		int parsedReviewCount = Objects.nonNull(reviewCount) ? Integer.parseInt(reviewCount) : 0;
		location.setReviewCount(parsedReviewCount);
		location.setRating(parsedRating);
		location.setPrice(getValueFromChildElement(node, "price"));
		
		//Parse reviews
		List<Review> reviews = getObjectListFromChildElement(node, "reviews",
				"review", new ReviewXMLReader(), provenanceInfo);
		location.setReviews(reviews);
		return location;
	}

	@Override
	public Location createInstanceForFusion(RecordGroup<Location, Attribute> cluster) {
		StringBuilder builder = new StringBuilder();
		for(Location location : cluster.getRecords()) {
			builder.append(location.getIdentifier());
			builder.append("_");
		}
		//Create empty instances of class attributes
		Location location = new Location(builder.toString(), "fused");
		return location;
	}
	
	public List<String> extractPhotoUrls(Node node) {
		List<String> photoUrls = new ArrayList<>();
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node photoNode = children.item(i);
			if(photoNode.getNodeName().equals("photos")) {
				NodeList tmpNodeList = photoNode.getChildNodes();
				for (int j = 0; j < tmpNodeList.getLength(); j++) {
					Node urlNode = tmpNodeList.item(j);
					if(urlNode.getNodeName().equals("url")) {
						photoUrls.add(urlNode.getTextContent());
					}
				}
			}
		}
		return photoUrls;
	}
	
	
	public OpeningHours extractOpeningHours(Node node, Pair<String, DayOfWeek> weekday) {
		OpeningHours openingHours = new OpeningHours();
		openingHours.setDayOfWeek(weekday.getSecond());
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node ohNode = children.item(i);
			if(ohNode.getNodeName().equals("openinghours")) {
				NodeList tmpNodeList = ohNode.getChildNodes();
				for (int j = 0; j < tmpNodeList.getLength(); j++) {
					Node tmpNode = tmpNodeList.item(j);
					if(tmpNode.getNodeName().equals(weekday.getFirst())) {
						NodeList weekdayElements = tmpNode.getChildNodes();
						for (int k = 0; k < weekdayElements.getLength(); k++) {
							Node element = weekdayElements.item(k);
							if(element.getNodeName().equals("from")) {
								try {
									openingHours.setFrom(LocalTime.parse(element.getTextContent(), germanFormatter));
								} catch(DateTimeParseException ex) {
									fromReadingIgnores += 1;
								}
							}
							else if(element.getNodeName().equals("to")) {
								try {
									openingHours.setTo(LocalTime.parse(element.getTextContent(), germanFormatter));
								} catch(DateTimeParseException ex) {
									toReadingIgnores += 1;
								}
							}
						}
					}
				}
			}
		}
		return openingHours;
	}

	public int getFromReadingIgnores() {
		return fromReadingIgnores;
	}

	public void setFromReadingIgnores(int fromReadingIgnores) {
		this.fromReadingIgnores = fromReadingIgnores;
	}

	public int getToReadingIgnores() {
		return toReadingIgnores;
	}

	public void setToReadingIgnores(int toReadingIgnores) {
		this.toReadingIgnores = toReadingIgnores;
	}
	
}
