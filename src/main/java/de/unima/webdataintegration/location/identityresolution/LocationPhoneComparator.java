package de.unima.webdataintegration.location.identityresolution;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationPhoneComparator implements Comparator<Location, Attribute>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3337073195037216870L;
	private LevenshteinSimilarity similarity;
	
	public LocationPhoneComparator() {
		this.similarity = new LevenshteinSimilarity();
	}
	
	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		String normPhone1 = Objects.nonNull(record1.getPhone()) ? 
				record1.getPhone().replaceAll("\\+", "").trim() : "";
		String normPhone2 = Objects.nonNull(record2.getPhone()) ? 
				record2.getPhone().replaceAll("\\+", "").trim() : "";
		return similarity.calculate(normPhone1, normPhone2);
	}

}
