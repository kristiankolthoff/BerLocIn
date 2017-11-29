package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationWebsiteLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity similarity;
	
	
	public LocationWebsiteLevenshteinComparator() {
		this.similarity = new LevenshteinSimilarity();
	}


	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(Objects.isNull(record1.getWebsite()) || Objects.isNull(record2.getWebsite())) {
			return 0;
		}
		//Normalize website1
		String website1 = record1.getWebsite().trim();
		//Normalize website2
		String website2 = record2.getWebsite().trim();
		return similarity.calculate(website1, website2);
	}

}
