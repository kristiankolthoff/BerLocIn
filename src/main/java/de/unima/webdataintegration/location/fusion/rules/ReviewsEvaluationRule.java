package de.unima.webdataintegration.location.fusion.rules;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.Review;

public class ReviewsEvaluationRule extends EvaluationRule<Location, Attribute>{

	@Override
	public boolean isEqual(Location record1, Location record2, Attribute schemaElement) {
		if(!record1.hasValue(Location.REVIEWS) || !record2.hasValue(Location.REVIEWS)) return false;
		Set<Review> reviews1 = new HashSet<>();
		for (Review review : record1.getReviews()) {
			reviews1.add(review);
		}
		Set<Review> reviews2 = new HashSet<>();
		for (Review review : record2.getReviews()) {
			reviews2.add(review);
		}
		return reviews1.containsAll(reviews2) && reviews2.containsAll(reviews1);
	}

	@Override
	public boolean isEqual(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
