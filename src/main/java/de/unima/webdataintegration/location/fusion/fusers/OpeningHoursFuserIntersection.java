package de.unima.webdataintegration.location.fusion.fusers;

import java.util.List;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Intersection;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.OpeningHours;

public class OpeningHoursFuserIntersection extends AttributeValueFuser<List<OpeningHours>, Location, Attribute> {

	public OpeningHoursFuserIntersection() {
		super(new Intersection<OpeningHours, Location, Attribute>());
	}

	@Override
	protected List<OpeningHours> getValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return null;
	}

	@Override
	public void fuse(RecordGroup<Location, Attribute> group, Location fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<List<OpeningHours>, Location, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
//		fusedRecord.setOpeningHours(fused.getValue());
		fusedRecord
				.setAttributeProvenance(Location.OPENING_HOURS, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Location.OPENING_HOURS);
	}
	
}
