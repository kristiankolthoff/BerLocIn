package de.unima.webdataintegration.location.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Objects;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class ReviewXMLReader extends XMLMatchableReader<Review, Attribute> implements
				FusibleFactory<Review, Attribute>{

	private DateTimeFormatter formatter;
	
	public ReviewXMLReader() {
		formatter = new DateTimeFormatterBuilder()
		        .appendPattern("d. MMMM yyyy")
		        .toFormatter(Locale.GERMAN);
	}

	@Override
	protected void initialiseDataset(DataSet<Review, Attribute> dataset) {
		super.initialiseDataset(dataset);
		dataset.addAttribute(Review.USER_NAME);
		dataset.addAttribute(Review.USER_IMG);
		dataset.addAttribute(Review.HEADING);
		dataset.addAttribute(Review.RATING);
		dataset.addAttribute(Review.CONTENT);
		dataset.addAttribute(Review.DATE);
	}

	@Override
	public Review createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		Review review = new Review(id, provenanceInfo);
		review.setUserName(getValueFromChildElement(node, "username"));
		review.setUserImage(getValueFromChildElement(node, "userimg"));
		review.setHeading(getValueFromChildElement(node, "heading"));
		String rating = getValueFromChildElement(node, "rating");
		double ratingParsed = Objects.nonNull(rating) ? Double.parseDouble(rating) : 0d;
		review.setRating(ratingParsed);
		review.setContent(getValueFromChildElement(node, "content"));
		String date = getValueFromChildElement(node, "date");
		LocalDate dateParsed = LocalDate.parse(date, formatter);
		review.setDate(dateParsed);
		return review;
	}

	@Override
	public Review createInstanceForFusion(RecordGroup<Review, Attribute> cluster) {
		StringBuilder builder = new StringBuilder();
		for(Review district : cluster.getRecords()) {
			builder.append(district.getIdentifier());
			builder.append("_");
		}
		Review district = new Review(builder.toString(), "fused");
		return district;
	}

}
