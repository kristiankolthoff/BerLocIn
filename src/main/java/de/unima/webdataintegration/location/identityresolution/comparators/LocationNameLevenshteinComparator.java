package de.unima.webdataintegration.location.identityresolution.comparators;

import org.jsoup.Jsoup;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationNameLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = -7158641405132212257L;
	private LevenshteinSimilarity similarity;
	
	public LocationNameLevenshteinComparator() {
		this.similarity = new LevenshteinSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
//		String name1 = Jsoup.parse(record1.getName()).text();
//		String name2 = Jsoup.parse(record2.getName()).text();
		return similarity.calculate(record1.getName(), record2.getName());
	}

}
