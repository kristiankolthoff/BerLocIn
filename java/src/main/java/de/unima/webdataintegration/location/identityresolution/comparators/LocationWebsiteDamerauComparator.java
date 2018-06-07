package de.unima.webdataintegration.location.identityresolution.comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;
import info.debatty.java.stringsimilarity.Damerau;

public class LocationWebsiteDamerauComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private Damerau damerau;
	
	
	public LocationWebsiteDamerauComparator() {
		this.damerau = new Damerau();
	}


	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.WEBSITE) || !record2.hasValue(Location.WEBSITE)) {
			return -1;
		}
		//Normalize website1
		String website1 = record1.getWebsite().toLowerCase().trim();
		//Normalize website2
		String website2 = record2.getWebsite().toLowerCase().trim();
		double distance = damerau.distance(website1, website2);
		double max = Math.max(website1.length(), website2.length());
		return 1 - distance / max;
	}

}
