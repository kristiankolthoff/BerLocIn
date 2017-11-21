package de.unima.webdataintegration.location.model;

import java.util.Objects;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class LocationXMLReader extends XMLMatchableReader<Location, Attribute> implements
				FusibleFactory<Location, Attribute>{

	@Override
	protected void initialiseDataset(DataSet<Location, Attribute> dataset) {
		super.initialiseDataset(dataset);
		dataset.addAttribute(Location.NAME);
		dataset.addAttribute(Location.TYPE);
		dataset.addAttribute(Location.CONTACT);
		dataset.addAttribute(Location.ADDRESS);
		dataset.addAttribute(Location.OPENING_HOURS);
		dataset.addAttribute(Location.POPULAR_HOURS);
		dataset.addAttribute(Location.RATING);
		dataset.addAttribute(Location.REVIEW_COUNT);
		dataset.addAttribute(Location.PRICE);
		dataset.addAttribute(Location.PHOTO_URLS);
		dataset.addAttribute(Location.DISTRICT);
	}

	@Override
	public Location createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		Location location = new Location(id, provenanceInfo);
		location.setName(getValueFromChildElement(node, "name"));
		location.setType(getValueFromChildElement(node, "type"));
		
		//Parse contact information
		location.setContact(getContact(node, provenanceInfo));
		
		//Parse address information
		Address address = getAddress(node, provenanceInfo);
		location.setAddress(address);
		
		//Parse rating and review_count
		String rating = getValueFromChildElement(node, "rating");
		double parsedRating = Objects.nonNull(rating) ? Double.parseDouble(rating) : 0d;
		String reviewCount = getValueFromChildElement(node, "review_count");
		int parsedReviewCount = Objects.nonNull(reviewCount) ? Integer.parseInt(reviewCount) : 0;
		location.setReviewCount(parsedReviewCount);
		location.setRating(parsedRating);
		location.setPrice(getValueFromChildElement(node, "price"));
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
		location.setContact(new Contact());
		location.setAddress(new Address());
		return location;
	}
	
	public Contact getContact(Node node, String provenanceInfo) {
		Contact contact = new Contact();
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node tmpNode = children.item(i);
			if(tmpNode.getNodeName().equals("contact")) {
				NodeList contactElements = tmpNode.getChildNodes();
				for (int j = 0; j < contactElements.getLength(); j++) {
					Node element = contactElements.item(j);
					if(element.getNodeName().equals("email")) contact.setEmail(element.getTextContent());
					else if(element.getNodeName().equals("website")) contact.setWebsite(element.getTextContent());
					else if(element.getNodeName().equals("phone")) contact.setPhone(element.getTextContent());
				}
			}
		}
		return (contact.getEmail() == null && contact.getWebsite() == null &&
				contact.getPhone() == null) ? null : contact;
	}
	
	public Address getAddress(Node node, String provenanceInfo) {
		Address address = new Address();
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node tmpNode = children.item(i);
			if(tmpNode.getNodeName().equals("address")) {
				NodeList contactElements = tmpNode.getChildNodes();
				for (int j = 0; j < contactElements.getLength(); j++) {
					Node element = contactElements.item(j);
					if(element.getNodeName().equals("streetAddress")) address.setStreetAddress(element.getTextContent());
					else if(element.getNodeName().equals("postalCode")) address.setPostalCode(element.getTextContent());
					else if(element.getNodeName().equals("city")) address.setCity(element.getTextContent());
					else if(element.getNodeName().equals("country")) address.setCountry(element.getTextContent());
					else if(element.getNodeName().equals("latitude")) {
						String latitude = element.getTextContent();
						double parsedLatitude = Objects.nonNull(latitude) ? Double.parseDouble(latitude) : 0d;
						address.setLatitude(parsedLatitude);
					}
					else if(element.getNodeName().equals("longitude")) {
						String longitude = element.getTextContent();
						double parsedLongitude = Objects.nonNull(longitude) ? Double.parseDouble(longitude) : 0d;
						address.setLongitude(parsedLongitude);
					}
				}
			}
		}
		return (address.getStreetAddress() == null && address.getPostalCode() == null &&
				address.getCity() == null && address.getCountry() == null &&
				address.getLatitude() == 0d && address.getLongitude() == 0d) ? null : address;
	}
	
}
