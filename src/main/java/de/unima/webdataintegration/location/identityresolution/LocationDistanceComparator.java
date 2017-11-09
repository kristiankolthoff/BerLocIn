package de.unima.webdataintegration.location.identityresolution;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class LocationDistanceComparator implements Comparator<Location, Attribute>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6667694581913594885L;

	private GeodeticCalculator geoCalculator;
	private Ellipsoid reference;
	
	public LocationDistanceComparator() {
		this.geoCalculator = new GeodeticCalculator();
		this.reference = Ellipsoid.WGS84;
	}
	
	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		GlobalPosition position1 = new GlobalPosition(record1.getLatitude(), record1.getLongitude(), 0d);
		GlobalPosition position2 = new GlobalPosition(record2.getLatitude(), record2.getLongitude(), 0d);
		double distance = geoCalculator.calculateGeodeticCurve(reference, position1, position2)
							.getEllipsoidalDistance();
		double similarity = 1 - (distance / 100);
		return (similarity < 0d) ? 0d : similarity;
	}

}
