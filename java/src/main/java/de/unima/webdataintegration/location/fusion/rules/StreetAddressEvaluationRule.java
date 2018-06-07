package de.unima.webdataintegration.location.fusion.rules;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class StreetAddressEvaluationRule extends EvaluationRule<Location, Attribute>{

	@Override
	public boolean isEqual(Location record1, Location record2, Attribute schemaElement) {
		return record1.getStreetAddress().equals(record2.getStreetAddress());
	}

	@Override
	public boolean isEqual(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
