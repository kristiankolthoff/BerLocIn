package de.unima.webdataintegration.location.identityresolution.comparators;

import java.util.Objects;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Address;
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
		if(Objects.isNull(record1.getAddress()) || Objects.isNull(record2.getAddress())) {
			return 0;
		}
		Address address1 = record1.getAddress();
		Address address2 = record2.getAddress();
		if(address1.getLatitude() == 0d || address1.getLongitude() == 0d ||
				address2.getLatitude() == 0d || address2.getLongitude() == 0d) {
			return 0;
		}
		GlobalPosition position1 = new GlobalPosition(address1.getLatitude(), address1.getLongitude(), 0d);
		GlobalPosition position2 = new GlobalPosition(address2.getLatitude(), address2.getLongitude(), 0d);
		double distance = geoCalculator.calculateGeodeticCurve(reference, position1, position2)
							.getEllipsoidalDistance();
		double similarity = 1 - (distance / maximumDistance);
		return (similarity < 0d) ? 0d : similarity;
	}

}
