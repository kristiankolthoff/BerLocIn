package de.unima.webdataintegration.location.identityresolution.comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationStreetAddressLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity similarity;
	
	
	public LocationStreetAddressLevenshteinComparator() {
		this.similarity = new LevenshteinSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.STREET_ADDRESS) || !record2.hasValue(Location.STREET_ADDRESS)) {
			return -1;
		}
		//Normalize streetAddress1
		String streetAddress1 = record1.getStreetAddress().trim();
		//Normalize streetAddress2
		String streetAddress2 = record2.getStreetAddress().trim();
		return similarity.calculate(streetAddress1, streetAddress2);
	}

}
