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
		if(Objects.isNull(record1.getPostalCode()) || Objects.isNull(record2.getPostalCode())) {
			return 0;
		}
		//Normalize streetAddress1
		String streetAddress1 = record1.getPostalCode().trim();
		//Normalize streetAddress2
		String streetAddress2 = record2.getPostalCode().trim();
		return similarity.calculate(streetAddress1, streetAddress2);
	}

}
