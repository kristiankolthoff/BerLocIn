package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationWebsiteBaseLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity similarity;
	
	public LocationWebsiteBaseLevenshteinComparator() {
		this.similarity = new LevenshteinSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(Objects.isNull(record1.getContact()) || Objects.isNull(record2.getContact())) {
			return 0;
		}
		//Normalize website1
		String website1 = Objects.nonNull(record1.getContact().getWebsite()) ? 
				record1.getContact().getWebsite().trim() : "";
		String[] urlComponents1 = website1.split("\\.");
		if(urlComponents1.length > 2) {
			website1 = urlComponents1[urlComponents1.length-2];
		}
		//Normalize website2
		String website2 = Objects.nonNull(record2.getContact().getWebsite()) ? 
				record2.getContact().getWebsite().trim() : "";
		String[] urlComponents2 = website2.split("\\.");
		if(urlComponents2.length > 2) {
			website2 = urlComponents2[urlComponents2.length-2];
		}
		return similarity.calculate(website1, website2);
	}

}
