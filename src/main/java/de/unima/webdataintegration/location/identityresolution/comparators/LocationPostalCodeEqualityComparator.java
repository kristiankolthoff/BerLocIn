package de.unima.webdataintegration.location.identityresolution.comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class LocationPostalCodeEqualityComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	
	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.POSTAL_CODE) || !record2.hasValue(Location.POSTAL_CODE)) {
			return -1;
		}
		//Normalize streetAddress1
		String streetAddress1 = record1.getPostalCode().trim();
		//Normalize streetAddress2
		String streetAddress2 = record2.getPostalCode().trim();
		return (streetAddress1.equals(streetAddress2)) ? 1 : 0;
	}

}
