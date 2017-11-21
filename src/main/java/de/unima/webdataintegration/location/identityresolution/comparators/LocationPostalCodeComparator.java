package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationPostalCodeComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity similarity;
	
	
	public LocationPostalCodeComparator() {
		this.similarity = new LevenshteinSimilarity();
	}


	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(Objects.isNull(record1.getAddress()) || Objects.isNull(record2.getAddress())) {
			return 0;
		}
		//Normalize streetAddress1
		String streetAddress1 = Objects.nonNull(record1.getAddress().getStreetAddress()) ? 
				record1.getAddress().getStreetAddress().trim() : "";
		//Normalize streetAddress2
		String streetAddress2 = Objects.nonNull(record2.getAddress().getStreetAddress()) ? 
				record2.getAddress().getStreetAddress().trim() : "";
		return similarity.calculate(streetAddress1, streetAddress2);
	}

}
