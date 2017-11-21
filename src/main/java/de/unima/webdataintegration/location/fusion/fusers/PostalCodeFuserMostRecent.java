package de.unima.webdataintegration.location.fusion.fusers;


import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.MostRecent;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Address;
import de.unima.webdataintegration.location.model.Location;

public class PostalCodeFuserMostRecent extends AttributeValueFuser<String, Location, Attribute>{

	public PostalCodeFuserMostRecent() {
		super(new MostRecent<>());
	}

	@Override
	protected String getValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getAddress().getPostalCode();
	}

	@Override
	public void fuse(RecordGroup<Location, Attribute> group, Location fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<String, Location, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.getAddress().setPostalCode(fused.getValue());
	}

	@Override
	public boolean hasValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Location.ADDRESS) && record.getAddress().hasValue(Address.POSTAL_CODE);
	}

}