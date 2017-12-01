package de.unima.webdataintegration.location.fusion.rules;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.PercentageSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class RatingEvaluationRule extends EvaluationRule<Location, Attribute>{

	private static PercentageSimilarity similarity =
			new PercentageSimilarity(10);
	
	@Override
	public boolean isEqual(Location record1, Location record2, Attribute schemaElement) {
		return similarity.calculate(record1.getRating(), record2.getRating()) > 0.8 ? true : false;
	}

	@Override
	public boolean isEqual(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
