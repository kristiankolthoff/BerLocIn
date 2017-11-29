package de.unima.webdataintegration.location.identityresolution.blockingkeys;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;
import javafx.geometry.Rectangle2D;

public class LocationBlockingKeyByArea extends RecordBlockingKeyGenerator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private Rectangle2D boundingBox;
	private int stepsX;
	private int stepsY;
	private Rectangle2D[][] boxes;

	public LocationBlockingKeyByArea(float latitudeLeft, float longitudeLeft, 
			float latitudeRight, float longitudeRight, int stepsX, int stepsY) {
		this.stepsX = stepsX;
		this.stepsY = stepsY;
		//Since differences in coordinates are very small in city radius, multiply values with constant value
		this.boundingBox = new Rectangle2D(latitudeLeft, longitudeLeft, 
				latitudeRight-latitudeLeft, longitudeRight-longitudeLeft);
		this.boxes = computeBoxes(boundingBox, this.stepsX, this.stepsY);
	}


	private Rectangle2D[][] computeBoxes(Rectangle2D boundingBox, int stepsX, int stepsY) {
		Rectangle2D[][] boxes = new Rectangle2D[stepsX][stepsY];
		double incX = boundingBox.getWidth() / stepsX;
		double incY = boundingBox.getHeight() / stepsY;
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes.length; j++) {
				boxes[j][i] = new Rectangle2D(incX * j, incY * i, incX, incY);
			}
		}
		return boxes;
	}


	@Override
	public void generateBlockingKeys(Location record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Location>> resultCollector) {
		if(record.hasValue(Location.LATITUDE) 
				&& record.hasValue(Location.LONGITUDE)) {
			double latitude = record.getLatitude();
			double longitude = record.getLongitude();
			//Width and height have to be adapted to multiplying constant
			final Rectangle2D point = new Rectangle2D(latitude, longitude, 0.01, 0.01);
			for (int i = 0; i < boxes[0].length; i++) {
				for (int j = 0; j < boxes.length; j++) {
					if(boxes[j][i].intersects(point)) {
						resultCollector.next(new Pair<String, Location>(j + " " + i, record));
						return;
					}
				}
			}
		} else {
			resultCollector.next(new Pair<String, Location>("static", record));
		}
	}

}
