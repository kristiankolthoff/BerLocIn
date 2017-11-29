package de.unima.webdataintegration.location.identityresolution.comparators;


import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationEmailBaseLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity similarity;
	
	public LocationEmailBaseLevenshteinComparator() {
		this.similarity = new LevenshteinSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.EMAIL) || !record2.hasValue(Location.EMAIL)) {
			return -1;
		}
		//Normalize email1
		String email1 = record1.getEmail().trim();
		String[] urlComponents1 = email1.split("@");
		if(urlComponents1.length > 1) {
			email1 = urlComponents1[urlComponents1.length-1];
		}
		//Normalize email2
		String email2 = record2.getEmail().trim();
		String[] urlComponents2 = email2.split("@");
		if(urlComponents2.length > 1) {
			email2 = urlComponents2[urlComponents2.length-1];
		}
		return similarity.calculate(email1, email2);
	}

}
