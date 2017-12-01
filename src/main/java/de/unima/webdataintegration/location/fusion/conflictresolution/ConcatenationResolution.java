package de.unima.webdataintegration.location.fusion.conflictresolution;

import java.util.Collection;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class ConcatenationResolution extends ConflictResolutionFunction<String, Location, Attribute>{


	@Override
	public FusedValue<String, Location, Attribute> resolveConflict(
			Collection<FusibleValue<String, Location, Attribute>> values) {
		StringBuilder builder = new StringBuilder();
		for (FusibleValue<String, Location, Attribute> value : values) {
			builder.append(value.getValue() + ",");
		}
		String typesConcatentation = builder.toString();
		return new FusedValue<>(typesConcatentation.substring(0,typesConcatentation.length()-1));
	}

}
