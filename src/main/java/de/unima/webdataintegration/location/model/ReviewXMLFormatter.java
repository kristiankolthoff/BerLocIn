package de.unima.webdataintegration.location.model;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class ReviewXMLFormatter extends XMLFormatter<Review>{

	private DateTimeFormatter formatter;
	
	public ReviewXMLFormatter() {
		formatter = new DateTimeFormatterBuilder()
		        .appendPattern("d. MMMM yyyy")
		        .toFormatter(Locale.GERMAN);
	}

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("reviews");
	}

	@Override
	public Element createElementFromRecord(Review record, Document doc) {
		Element review = doc.createElement("review");
		review.appendChild(createTextElement("username", record.getUserName(), doc));
		review.appendChild(createTextElement("userimg", record.getUserImage(), doc));
		review.appendChild(createTextElement("heading", record.getHeading(), doc));
		review.appendChild(createTextElement("rating", String.valueOf(record.getRating()), doc));
		review.appendChild(createTextElement("content", record.getContent(), doc));
		review.appendChild(createTextElement("date", record.getDate().format(formatter), doc));
		return review;
	}

}
