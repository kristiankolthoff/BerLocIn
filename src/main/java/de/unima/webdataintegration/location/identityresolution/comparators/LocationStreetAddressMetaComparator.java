package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.PercentageSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.JaccardOnNGramsSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationStreetAddressMetaComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity nameSimilarity;
	private PercentageSimilarity numberSimilarity;
	private float beta;
	
	public LocationStreetAddressMetaComparator(float beta) {
		this.nameSimilarity = new LevenshteinSimilarity();
		this.numberSimilarity = new PercentageSimilarity(0.3);
		this.beta = beta;
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(Objects.isNull(record1.getStreetAddress()) || Objects.isNull(record2.getStreetAddress())) {
			return 0;
		}
		
		//Normalize streetAddress1
		String streetAddress1 = record1.getStreetAddress().trim();
		String[] streetComponents1 = streetAddress1.split(" ");
		String[] streetNumber1 = extractFeatures(streetComponents1);
		
		//Normalize streetAddress2
		String streetAddress2 = record2.getStreetAddress().trim();
		String[] streetComponents2 = streetAddress2.split(" ");
		String[] streetNumber2 = extractFeatures(streetComponents2);
		//Compute both separated similarity scores
		double similarityName = nameSimilarity.calculate(streetNumber1[0], streetNumber2[0]);
		double similarityNumber = numberSimilarity.calculate(Double.valueOf(streetNumber1[1]), 
				Double.valueOf(streetNumber2[1]));
		//Combine both similarity scores by using f-beta-measure
		return ((1 + Math.pow(beta, 2)) * similarityName * similarityNumber) / 
				((Math.pow(beta, 2) *similarityName) + similarityNumber);
	}

	private String[] extractFeatures(String[] streetComponents) {
		String name = "", number = "";
		for (int i = 0; i < streetComponents.length; i++) {
			if(streetComponents[i].matches("^[+-]?\\d+$")) {
				number = streetComponents[i];
			} else {
				name += streetComponents[i] + " ";
			}
		}
		return new String[] {name.trim(), number.trim()};
	}
	
//	public String preprocess(String streetAddress) {
//		return streetAddress.replaceAll(regex, replacement)
//	}

}
