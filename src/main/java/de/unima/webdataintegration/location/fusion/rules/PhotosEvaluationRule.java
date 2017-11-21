package de.unima.webdataintegration.location.fusion.rules;

import java.util.List;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class PhotosEvaluationRule extends EvaluationRule<Location, Attribute>{

	@Override
	public boolean isEqual(Location record1, Location record2, Attribute schemaElement) {
		@SuppressWarnings("unused")
		List<String> photos1 = record1.getPhotoUrls();
		@SuppressWarnings("unused")
		List<String> photos2 = record2.getPhotoUrls();
		//Download images and compute similarity measures of the cross product to determine similarity
		//TODO implement image similarity measures using opencv
		return false;
	}

	@Override
	public boolean isEqual(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
