package de.unima.webdataintegration.location.identityresolution.comparators;


import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class LocationDistanceComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 6667694581913594885L;

	private GeodeticCalculator geoCalculator;
	private Ellipsoid reference;
	private double maximumDistance;
	
	public LocationDistanceComparator(double maximumDistance) {
		this.geoCalculator = new GeodeticCalculator();
		this.reference = Ellipsoid.WGS84;
		this.maximumDistance = maximumDistance;
	}
	
	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.LATITUDE) || !record1.hasValue(Location.LONGITUDE)
				|| !record2.hasValue(Location.LATITUDE) || !record2.hasValue(Location.LONGITUDE)) {
			return -1;
		}
		GlobalPosition position1 = new GlobalPosition(record1.getLatitude(), record1.getLongitude(), 0d);
		GlobalPosition position2 = new GlobalPosition(record2.getLatitude(), record2.getLongitude(), 0d);
		double distance = geoCalculator.calculateGeodeticCurve(reference, position1, position2)
							.getEllipsoidalDistance();
		return (distance > maximumDistance) ? 0 : (1 - distance / maximumDistance);
	}

}
