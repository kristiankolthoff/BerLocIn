package de.unima.webdataintegration.location.identityresolution;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationNameComparator implements Comparator<Location, Attribute>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7158641405132212257L;
	private LevenshteinSimilarity similarity;
	
	public LocationNameComparator() {
		this.similarity = new LevenshteinSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		String name1 = record1.getName();
		String name2 = record2.getName();
		return similarity.calculate(name1, name2);
	}

}
