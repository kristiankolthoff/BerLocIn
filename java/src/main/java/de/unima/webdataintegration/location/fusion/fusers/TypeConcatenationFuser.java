package de.unima.webdataintegration.location.fusion.fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.fusion.conflictresolution.ConcatenationResolution;
import de.unima.webdataintegration.location.model.Location;

public class TypeConcatenationFuser extends AttributeValueFuser<String, Location, Attribute>{

	public TypeConcatenationFuser() {
		super(new ConcatenationResolution());
	}

	@Override
	protected String getValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getType();
	}

	@Override
	public void fuse(RecordGroup<Location, Attribute> group, Location fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<String, Location, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setType(fused.getValue());
	}

	@Override
	public boolean hasValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Location.TYPE);
	}

}

