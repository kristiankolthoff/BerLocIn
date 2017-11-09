package de.unima.webdataintegration.location.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class LocationXMLFormatter extends XMLFormatter<Location>{

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
		location.appendChild(createTextElement("openingHours", record.getOpeningHours(), doc));
		location.appendChild(createTextElement("website", record.getWebsite(), doc));
		location.appendChild(createTextElement("popularHours", record.getPopularHours(), doc));
		location.appendChild(createTextElement("latitude", String.valueOf(record.getLatitude()), doc));
		location.appendChild(createTextElement("longitude", String.valueOf(record.getLongitude()), doc));
		location.appendChild(createTextElement("rating", String.valueOf(record.getRating()), doc));
		location.appendChild(createTextElement("reviewCount", String.valueOf(record.getReviewCount()), doc));
		location.appendChild(createTextElement("phone", record.getPhone(), doc));
		location.appendChild(createTextElement("address", record.getPhone(), doc));
		location.appendChild(createTextElement("price", record.getPrice(), doc));
		return location;
	}

}
