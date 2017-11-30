package de.unima.webdataintegration.location.identityresolution.comparators;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationWebsiteBaseLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity similarity;
	
	public LocationWebsiteBaseLevenshteinComparator() {
		this.similarity = new LevenshteinSimilarity();
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.WEBSITE) || !record2.hasValue(Location.WEBSITE)) {
			return -1;
		}
		//Normalize website1
		String website1 = record1.getWebsite().toLowerCase().trim();
		String base1 = preprocess(website1);
		//Normalize website2
		String website2 = record2.getWebsite().toLowerCase().trim();
		String base2 = preprocess(website2);
		if(Objects.isNull(base1) || Objects.isNull(base2)) {
			return similarity.calculate(website1, website2);
		}
		return similarity.calculate(base1, base2);
	}
	
	public String preprocess(String website) {
		try {
			URI uri = new URI(website);
			String hostname = uri.getHost();
			if (hostname != null) {
			      return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
			}
			return hostname;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

}
