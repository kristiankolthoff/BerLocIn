package de.unima.webdataintegration.location.identityresolution.comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class LocationStreetGPSFallbackComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private double minimumThreshold;
	private Comparator<Location, Attribute> streetComparator;
	private Comparator<Location, Attribute> distanceComparator;
	
	public LocationStreetGPSFallbackComparator(double minimumThreshold, 
			Comparator<Location, Attribute> streetComparator,
			Comparator<Location, Attribute> distanceComparator) {
		this.setMinimumThreshold(minimumThreshold);
		this.streetComparator = streetComparator;
		this.distanceComparator = distanceComparator;
	}


	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		double simStreet = streetComparator.compare(record1, record2, schemaCorrespondence);
		if(record1.hasValue(Location.LATITUDE) && record1.hasValue(Location.LONGITUDE)
				&& record2.hasValue(Location.LATITUDE) && record2.hasValue(Location.LONGITUDE)) {
				return (simStreet < minimumThreshold) ? 
						distanceComparator.compare(record1, record2, schemaCorrespondence) :
						simStreet;
		}
		return simStreet;
	}


	public double getMinimumThreshold() {
		return minimumThreshold;
	}


	public void setMinimumThreshold(double minimumThreshold) {
		this.minimumThreshold = minimumThreshold;
	}

}
