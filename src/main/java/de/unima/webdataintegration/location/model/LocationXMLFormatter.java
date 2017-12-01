package de.unima.webdataintegration.location.model;

import java.time.DayOfWeek;
import java.util.Objects;
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
					//Include from time only if its available in the fused record
					if(Objects.nonNull(openHours.getFrom())) {
						Element from = doc.createElement("from");
						from.appendChild(doc.createTextNode(openHours.getFrom().toString()));
						elemWeekday.appendChild(from);
					}
					//Include to time only if its available in the fused record
					if(Objects.nonNull(openHours.getTo())) {
						Element to = doc.createElement("to");
						to.appendChild(doc.createTextNode(openHours.getTo().toString()));
						elemWeekday.appendChild(to);
					}
				}
			}
			openingHours.appendChild(elemWeekday);
		}
		location.appendChild(openingHours);
		try {
		location.appendChild(createReviewsElement(record, doc));
		}catch(Exception e) {
			e.printStackTrace();
		}
		location.appendChild(createPhotosElement(record, doc));
		return location;
	}
	
	protected Element createPhotosElement(Location record, Document doc) {
		Element root = doc.createElement("photos");
		for(String photoUrl : record.getPhotoUrls()) {
			Element url = doc.createElement("url");
			url.appendChild(doc.createTextNode(photoUrl));
			root.appendChild(url);
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
