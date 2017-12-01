package de.unima.webdataintegration.location.fusion.fusers;

import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.fusion.conflictresolution.OpeningHoursUnionResolution;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.OpeningHours;

public class OpeningHoursFuserUnion extends AttributeValueFuser<Set<OpeningHours>, Location, Attribute> {

	public OpeningHoursFuserUnion() {
		super(new OpeningHoursUnionResolution());
	}

	@Override
	protected Set<OpeningHours> getValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getOpeningHours();
	}

	@Override
	public void fuse(RecordGroup<Location, Attribute> group, Location fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<Set<OpeningHours>, Location, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setOpeningHours(fused.getValue());
		fusedRecord
				.setAttributeProvenance(Location.OPENING_HOURS, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Location.OPENING_HOURS);
	}
	
}
