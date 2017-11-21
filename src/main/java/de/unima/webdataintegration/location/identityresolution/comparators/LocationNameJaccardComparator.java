package de.unima.webdataintegration.location.identityresolution.comparators;

import org.jsoup.Jsoup;

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
		String name1 = Jsoup.parse(record1.getName()).text();
		String name2 = Jsoup.parse(record2.getName()).text();
		return similarity.calculate(name1, name2);
	}

}
