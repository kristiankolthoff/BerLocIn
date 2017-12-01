package de.unima.webdataintegration.location.fusion.fusers;

import java.util.List;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.Review;

public class ReviewsFuserUnion extends AttributeValueFuser<List<Review>, Location, Attribute> {

	public ReviewsFuserUnion() {
		super(new Union<Review, Location, Attribute>());
	}

	@Override
	protected List<Review> getValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getReviews();
	}

	@Override
	public void fuse(RecordGroup<Location, Attribute> group, Location fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<List<Review>, Location, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setReviews(fused.getValue());
		fusedRecord
				.setAttributeProvenance(Location.REVIEWS, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Location.REVIEWS);
	}
	
}
