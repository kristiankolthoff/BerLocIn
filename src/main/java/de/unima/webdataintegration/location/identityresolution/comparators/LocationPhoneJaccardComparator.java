package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.JaccardOnNGramsSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationPhoneJaccardComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 5076611326014071293L;
	private JaccardOnNGramsSimilarity similarity;
	
	public LocationPhoneJaccardComparator(int n) {
		this.similarity = new JaccardOnNGramsSimilarity(n);
	}
	
	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(Objects.isNull(record1.getPhone()) || Objects.isNull(record2.getPhone())) {
			return 0;
		}
		//Normalize phoneNumber1
		String normPhone1 = record1.getPhone().replaceAll("\\+", "").trim();
		
		//Normalize phoneNumber2
		String normPhone2 = record2.getPhone().replaceAll("\\+", "").trim();
		return similarity.calculate(normPhone1, normPhone2);
	}

}
