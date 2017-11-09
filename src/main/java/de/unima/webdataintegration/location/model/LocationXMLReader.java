package de.unima.webdataintegration.location.model;

import java.util.Objects;

import org.w3c.dom.Node;

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
		dataset.addAttribute(Location.LATITUDE);
		dataset.addAttribute(Location.LONGITUDE);
		dataset.addAttribute(Location.PHONE);
	}

	@Override
	public Location createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		Location location = new Location(id, provenanceInfo);
		location.setName(getValueFromChildElement(node, "name"));
		location.setType(getValueFromChildElement(node, "type"));
		location.setEmail(getValueFromChildElement(node,"email"));
		location.setOpeningHours(getValueFromChildElement(node, "openingHours"));
		location.setWebsite(getValueFromChildElement(node, "website"));
		location.setPopularHours(getValueFromChildElement(node, "popularHours"));
		
		//Parse latitude and longitude
		String latitude = getValueFromChildElement(node, "latitude");
		double parsedLatitude = Objects.nonNull(latitude) ? Double.parseDouble(latitude) : 0d;
		String longitude = getValueFromChildElement(node, "longitude");
		double parsedLongitude = Objects.nonNull(longitude) ? Double.parseDouble(longitude) : 0d;
		location.setLatitude(parsedLatitude);
		location.setLongitude(parsedLongitude);
		
		//Parse rating and review_count
		String rating = getValueFromChildElement(node, "rating");
		double parsedRating = Objects.nonNull(rating) ? Double.parseDouble(rating) : 0d;
		String reviewCount = getValueFromChildElement(node, "review_count");
		int parsedReviewCount = Objects.nonNull(reviewCount) ? Integer.parseInt(reviewCount) : 0;
		location.setReviewCount(parsedReviewCount);
		location.setRating(parsedRating);
		
		location.setPhone(getValueFromChildElement(node, "phone"));
		location.setAddress(getValueFromChildElement(node, "addres"));
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
		return new Location(builder.toString(), "fused");
	}

}
