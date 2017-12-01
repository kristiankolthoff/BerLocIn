package de.unima.webdataintegration.location.fusion.conflictresolution;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.OpeningHours;

public class OpeningHoursIntersectionResolution extends ConflictResolutionFunction<Set<OpeningHours>, Location, Attribute>{


	@Override
	public FusedValue<Set<OpeningHours>, Location, Attribute> resolveConflict(
			Collection<FusibleValue<Set<OpeningHours>, Location, Attribute>> values) {
		for (FusibleValue<Set<OpeningHours>, Location, Attribute> value : values) {
//			DayOfWeek
		}
		return null;

	}

}
