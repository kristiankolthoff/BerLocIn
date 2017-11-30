package de.unima.webdataintegration.location.identityresolution.comparators;


import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;
import info.debatty.java.stringsimilarity.Damerau;

public class LocationNameLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = -7158641405132212257L;
	private Damerau damerau;
	
	public LocationNameLevenshteinComparator() {
		this.damerau = new Damerau();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.NAME) || !record2.hasValue(Location.NAME)) {
			return -1;
		}
		String name1 = preprocess(record1.getName());
		String name2 = preprocess(record2.getName());
		double distance = damerau.distance(name1, name2);
		double max = Math.max(name1.length(), name2.length());
		return 1 - distance / max;
	}
	
	public String preprocess(String name) {
		return name.replaceAll("&amp;", "und")
				.toLowerCase()
				.replaceAll("cafe", "")
				.replaceAll("restaurant", "")
				.replaceAll("pizza", "")
				.replaceAll("hotel", "")
			.trim();
	}

}
