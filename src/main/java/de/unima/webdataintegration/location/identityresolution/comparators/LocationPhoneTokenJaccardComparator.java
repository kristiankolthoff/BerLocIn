package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationPhoneTokenJaccardComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 6076223596932781671L;
	private TokenizingJaccardSimilarity similarity;
	
	
	public LocationPhoneTokenJaccardComparator() {
		this.similarity = new TokenizingJaccardSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(Objects.isNull(record1.getContact()) || Objects.isNull(record2.getContact())) {
			return 0;
		}
		//Normalize phoneNumber1
		String normPhone1 = Objects.nonNull(record1.getContact().getPhone()) ? 
				record1.getContact().getPhone().replaceAll("\\+", "").trim() : "";
		
		//Normalize phoneNumber2
		String normPhone2 = Objects.nonNull(record2.getContact().getPhone()) ? 
				record2.getContact().getPhone().replaceAll("\\+", "").trim() : "";
		return similarity.calculate(normPhone1, normPhone2);
	}

}
