package de.unima.webdataintegration.location.identityresolution.comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationPhoneLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 3337073195037216870L;
	private LevenshteinSimilarity similarity;
	
	public LocationPhoneLevenshteinComparator() {
		this.similarity = new LevenshteinSimilarity();
	}
	
	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.PHONE) || !record2.hasValue(Location.PHONE)) {
			return -1;
		}
		String normPhone1 = preprocess(record1.getPhone());
		String normPhone2 = preprocess(record2.getPhone());
		return similarity.calculate(normPhone1, normPhone2);
	}
	
	public String preprocess(String name) {
		return name.replaceAll("[^\\d]", "")
				.trim();
	}

}
