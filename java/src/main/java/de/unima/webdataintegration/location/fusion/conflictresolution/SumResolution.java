package de.unima.webdataintegration.location.fusion.conflictresolution;

import java.util.Collection;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Fusible;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class SumResolution<RecordType extends Matchable & Fusible<SchemaElementType>, SchemaElementType extends Matchable> extends
	ConflictResolutionFunction<Double, RecordType, SchemaElementType>{


	@Override
	public FusedValue<Double, RecordType, SchemaElementType> resolveConflict(
			Collection<FusibleValue<Double, RecordType, SchemaElementType>> values) {
		double sum = 0;
		for (FusibleValue<Double, RecordType, SchemaElementType> value : values) {
			sum += value.getValue();
		}
		return new FusedValue<>(sum);
	}

}
