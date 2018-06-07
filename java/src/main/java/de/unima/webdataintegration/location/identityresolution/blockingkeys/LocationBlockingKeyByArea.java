package de.unima.webdataintegration.location.identityresolution.blockingkeys;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;

public class LocationBlockingKeyByArea extends RecordBlockingKeyGenerator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private int stepsX;
	private int stepsY;
	private double latitudeLeft;
	private double longitudeLeft;
	private double latitudeRight;
	private double longitudeRight;
	private double[] xValues;
	private double[] yValues;

	public LocationBlockingKeyByArea(double latitudeLeft, double longitudeLeft, 
			double latitudeRight, double longitudeRight, int stepsX, int stepsY) {
		this.stepsX = stepsX;
		this.stepsY = stepsY;
		this.latitudeLeft = latitudeLeft;
		this.longitudeLeft = longitudeLeft;
		this.latitudeRight = latitudeRight;
		this.longitudeRight = longitudeRight;
		computeBoxes(latitudeLeft, longitudeLeft, latitudeRight, longitudeRight, this.stepsX, this.stepsY);
	}


	private void computeBoxes(double latitudeLeft, double longitudeLeft, 
			double latitudeRight, double longitudeRight, int stepsX, int stepsY) {
		xValues = new double[stepsX + 1];
		yValues = new double[stepsY + 1];
		double incX = (latitudeLeft - latitudeRight) / stepsX;
		double incY = (longitudeRight - longitudeLeft) / stepsY;
		for (int i = 0; i < xValues.length; i++) {
			xValues[i] = latitudeLeft - incX * i;
		}
		for (int i = 0; i < yValues.length; i++) {
			yValues[i] = longitudeLeft + incY * i;
		}
	}


	@Override
	public void generateBlockingKeys(Location record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Location>> resultCollector) {
		if(record.hasValue(Location.LATITUDE) 
				&& record.hasValue(Location.LONGITUDE)) {
			double latitude = record.getLatitude();
			double longitude = record.getLongitude();
			resultCollector.next(new Pair<String, Location>(generateKeyByIndexes(latitude, longitude), record));
		} else {
			resultCollector.next(new Pair<String, Location>("static", record));
		}
	}
	
	public String generateKeyByIndexes(double latitude, double longitude) {
		int indexX = 0, indexY = 0;
		for (int i = 0; i < xValues.length; i++) {
			if(latitude > xValues[i]) {
				indexX = i - 1;
			}
		}
		for (int i = 0; i < yValues.length; i++) {
			if(longitude < yValues[i]) {
				indexY = i - 1;
			}
		}
		return String.valueOf(indexX) + "-" + String.valueOf(indexY);
	}


	public int getStepsX() {
		return stepsX;
	}


	public void setStepsX(int stepsX) {
		this.stepsX = stepsX;
	}


	public int getStepsY() {
		return stepsY;
	}


	public void setStepsY(int stepsY) {
		this.stepsY = stepsY;
	}


	public double getLatitudeLeft() {
		return latitudeLeft;
	}


	public void setLatitudeLeft(double latitudeLeft) {
		this.latitudeLeft = latitudeLeft;
	}


	public double getLongitudeLeft() {
		return longitudeLeft;
	}


	public void setLongitudeLeft(double longitudeLeft) {
		this.longitudeLeft = longitudeLeft;
	}


	public double getLatitudeRight() {
		return latitudeRight;
	}


	public void setLatitudeRight(double latitudeRight) {
		this.latitudeRight = latitudeRight;
	}


	public double getLongitudeRight() {
		return longitudeRight;
	}


	public void setLongitudeRight(double longitudeRight) {
		this.longitudeRight = longitudeRight;
	}
	
}
