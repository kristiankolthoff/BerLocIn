package de.unima.webdataintegration.location.model;

import java.time.DayOfWeek;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class LocationXMLFormatter extends XMLFormatter<Location>{

	
	private ReviewXMLFormatter reviewFormatter;
	
	public LocationXMLFormatter() {
		reviewFormatter = new ReviewXMLFormatter();
	}

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("locations");
	}

	@Override
	public Element createElementFromRecord(Location record, Document doc) {
		Element location = doc.createElement("location");
		location.appendChild(createTextElement("name", record.getName(), doc));
		location.appendChild(createTextElement("type", record.getType(), doc));
		location.appendChild(createTextElement("email", record.getEmail(), doc));
		location.appendChild(createTextElement("website", record.getWebsite(), doc));
		location.appendChild(createTextElement("phone", record.getPhone(), doc));
		location.appendChild(createTextElement("streetAddress", record.getStreetAddress(), doc));
		location.appendChild(createTextElement("postalCode", record.getPostalCode(), doc));
		location.appendChild(createTextElement("latitude", String.valueOf(record.getLatitude()), doc));
		location.appendChild(createTextElement("longitude", String.valueOf(record.getLongitude()), doc));
		location.appendChild(createTextElement("rating", String.valueOf(record.getRating()), doc));
		location.appendChild(createTextElement("reviewCount", String.valueOf(record.getReviewCount()), doc));
		location.appendChild(createTextElement("price", record.getPrice(), doc));
		Element openingHours = doc.createElement("openingHours");
		Set<OpeningHours> tmpOpeningHours = record.getOpeningHours();
		for (int i = 0; i < Location.WEEKDAYS.size(); i++) {
			Pair<String, DayOfWeek> weekday = Location.WEEKDAYS.get(i);
			Element elemWeekday = doc.createElement(weekday.getFirst());
			for(OpeningHours openHours : tmpOpeningHours) {
				if(openHours.getDayOfWeek() == weekday.getSecond()) {
					elemWeekday.appendChild(doc.createElement("from").appendChild(doc.createTextNode(
							openHours.getFrom().toString())));
					elemWeekday.appendChild(doc.createElement("to").appendChild(doc.createTextNode(
							openHours.getTo().toString())));
				}
			}
			openingHours.appendChild(elemWeekday);
		}
		location.appendChild(openingHours);
		location.appendChild(createReviewsElement(record, doc));
		location.appendChild(createPhotosElement(record, doc));
		return location;
	}
	
	protected Element createPhotosElement(Location record, Document doc) {
		Element root = doc.createElement("photos");
		for(String photoUrl : record.getPhotoUrls()) {
			root.appendChild(doc.createElement("url").appendChild(doc.createTextNode(photoUrl)));
		}
		return root;
	}
	
	protected Element createReviewsElement(Location record, Document doc) {
		Element reviewRoot = reviewFormatter.createRootElement(doc);
		reviewRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(Location.REVIEWS));

		for (Review review : record.getReviews()) {
			reviewRoot.appendChild(reviewFormatter
					.createElementFromRecord(review, doc));
		}

		return reviewRoot;
	}

}
