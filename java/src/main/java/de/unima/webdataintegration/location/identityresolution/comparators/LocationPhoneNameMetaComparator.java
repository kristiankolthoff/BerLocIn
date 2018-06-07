package de.unima.webdataintegration.location.identityresolution.comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class LocationPhoneNameMetaComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LocationPhoneSift4Comparator phoneSimilarity;
	private LocationNameDamerauComparator nameSimilartiy;
	
	public LocationPhoneNameMetaComparator() {
		this.phoneSimilarity = new LocationPhoneSift4Comparator(LocationPhoneSift4Comparator.REGION_DE, false);
		this.nameSimilartiy = new LocationNameDamerauComparator();
	}


	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		double phoneSim = phoneSimilarity.compare(record1, record2, schemaCorrespondence);
		if(phoneSim < 0) {
			return nameSimilartiy.compare(record1, record2, schemaCorrespondence);
		}
		return phoneSim;
	}

}
