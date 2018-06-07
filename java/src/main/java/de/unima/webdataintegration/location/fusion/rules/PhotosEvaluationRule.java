package de.unima.webdataintegration.location.fusion.rules;


import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class PhotosEvaluationRule extends EvaluationRule<Location, Attribute>{

	@Override
	public boolean isEqual(Location record1, Location record2, Attribute schemaElement) {
		Set<String> urls1 = new HashSet<>();
		for (String url : record1.getPhotoUrls()) {
			urls1.add(url);
		}
		Set<String> urls2 = new HashSet<>();
		for (String url : record2.getPhotoUrls()) {
			urls2.add(url);
		}
		return urls1.containsAll(urls2) && urls2.containsAll(urls1);
	}

	@Override
	public boolean isEqual(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
