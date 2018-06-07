package de.unima.webdataintegration.location.fusion.rules;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneSift4Comparator;
import de.unima.webdataintegration.location.model.Location;

public class PhoneEvaluationRule extends EvaluationRule<Location, Attribute>{

	private static LocationPhoneSift4Comparator comparator = 
			new LocationPhoneSift4Comparator(LocationPhoneSift4Comparator.REGION_DE, false);
	
	@Override
	public boolean isEqual(Location record1, Location record2, Attribute schemaElement) {
		return comparator.compare(record1, record2, null) > 0.95 ? true : false;
	}

	@Override
	public boolean isEqual(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
