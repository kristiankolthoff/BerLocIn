package de.unima.webdataintegration.location.fusion.conflictresolution;

import java.util.Collection;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;

public class NormalizedAverageResolution extends ConflictResolutionFunction<Double, Location, Attribute>{

	@Override
	public FusedValue<Double, Location, Attribute> resolveConflict(
			Collection<FusibleValue<Double, Location, Attribute>> values) {
		if (values.size() == 0) {
			return new FusedValue<>((Double) null);
		} else {

			double sum = 0.0;
			double count = 0.0;

			for (FusibleValue<Double, Location, Attribute> value : values) {
				sum += value.getValue() * value.getRecord().getReviewCount();
				count += value.getRecord().getReviewCount();
			}

			return new FusedValue<>(sum / count);

		}
	}

}
