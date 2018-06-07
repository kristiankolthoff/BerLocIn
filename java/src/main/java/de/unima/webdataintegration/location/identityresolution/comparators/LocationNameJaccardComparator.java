package de.unima.webdataintegration.location.identityresolution.comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.JaccardOnNGramsSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationNameJaccardComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private JaccardOnNGramsSimilarity similarity;
	
	public LocationNameJaccardComparator(int n) {
		this.similarity = new JaccardOnNGramsSimilarity(n);
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.NAME) || !record2.hasValue(Location.NAME)) {
			return -1;
		}
		String name1 = preprocess(record1.getName());
		String name2 = preprocess(record2.getName());
		return similarity.calculate(name1, name2);
	}

	public String preprocess(String name) {
		return name.replaceAll("&amp;", "und")
				.toLowerCase()
				.replaceAll("cafe", "")
				.replaceAll("restaurant", "")
				.replaceAll("hotel", "")
				.trim();
	}
}
