package de.unima.webdataintegration.location.fusion.fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Contact;
import de.unima.webdataintegration.location.model.Location;

public class PhoneFuserLongestString extends AttributeValueFuser<String, Location, Attribute>{

	public PhoneFuserLongestString() {
		super(new LongestString<>());
	}

	@Override
	protected String getValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getContact().getPhone();
	}

	@Override
	public void fuse(RecordGroup<Location, Attribute> group, Location fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<String, Location, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.getContact().setPhone(fused.getValue());
	}

	@Override
	public boolean hasValue(Location record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Location.CONTACT) && record.getContact().hasValue(Contact.PHONE);
	}

}
