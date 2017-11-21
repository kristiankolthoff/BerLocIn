package de.unima.webdataintegration.location.fusion.conflictresolution;

import java.util.Collection;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Fusible;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class SetIntersectionResolution<RecordType extends Matchable & Fusible<SchemaElementType>, SchemaElementType extends Matchable, T> extends
	ConflictResolutionFunction<Set<T>, RecordType, SchemaElementType>{

	@Override
	public FusedValue<Set<T>, RecordType, SchemaElementType> resolveConflict(
			Collection<FusibleValue<Set<T>, RecordType, SchemaElementType>> values) {
		// TODO Auto-generated method stub
		return null;
	}


}