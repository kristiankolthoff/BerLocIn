package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.JaccardOnNGramsSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationEmailJaccardComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private JaccardOnNGramsSimilarity similarity;
	
	
	public LocationEmailJaccardComparator(int n) {
		this.similarity = new JaccardOnNGramsSimilarity(n);
	}


	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(Objects.isNull(record1.getEmail()) || Objects.isNull(record2.getEmail())) {
			return 0;
		}
		//Normalize email1
		String email1 = record1.getEmail().trim();
		//Normalize email2
		String email2 = record2.getEmail().trim();
		return similarity.calculate(email1, email2);
	}

}
