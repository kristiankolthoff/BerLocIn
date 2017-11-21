package de.unima.webdataintegration.location.fusion.fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.numeric.Average;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Address;
import de.unima.webdataintegration.location.model.Location;

public class LongitudeFuserAverage extends AttributeValueFuser<Double, Location, Attribute>{

	public LongitudeFuserAverage() {
		super(new Average<>());
	}

	@Override
	protected Double getValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getAddress().getLongitude();
	}

	@Override
	public void fuse(RecordGroup<Location, Attribute> group, Location fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<Double, Location, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.getAddress().setLongitude(fused.getValue());
	}

	@Override
	public boolean hasValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Location.ADDRESS) && record.getAddress().hasValue(Address.LONGITUDE);
	}

}
