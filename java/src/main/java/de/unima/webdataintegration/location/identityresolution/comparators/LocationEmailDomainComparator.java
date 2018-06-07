package de.unima.webdataintegration.location.identityresolution.comparators;


import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationEmailDomainComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity similarity;
	
	public LocationEmailDomainComparator() {
		this.similarity = new LevenshteinSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.EMAIL) || !record2.hasValue(Location.EMAIL)) {
			return -1;
		}
		String[] components1 = preprocess(record1.getEmail());
		String[] components2 = preprocess(record2.getEmail());
		if(Objects.isNull(components1) || Objects.isNull(components2)) {
			return similarity.calculate(record1.getEmail(), record2.getEmail());
		}
		return similarity.calculate(components1[1], components2[1]);
	}
	
	public String[] preprocess(String email) {
		String email1 = email.trim().toLowerCase();
		String[] urlComponents1 = email1.split("@");
		if(urlComponents1.length > 1) {
			return urlComponents1;
		}
		return null;
	}

}
