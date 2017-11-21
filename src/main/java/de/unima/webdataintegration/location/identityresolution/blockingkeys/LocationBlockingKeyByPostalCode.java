package de.unima.webdataintegration.location.identityresolution.blockingkeys;

import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;

public class LocationBlockingKeyByPostalCode extends RecordBlockingKeyGenerator<Location, Attribute>{

	private static final long serialVersionUID = 1L;

	@Override
	public void generateBlockingKeys(Location record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Location>> resultCollector) {
		if(Objects.nonNull(record.getAddress()) && Objects.nonNull(record.getAddress().getPostalCode())) {
			resultCollector.next(new Pair<String, Location>(record.getAddress().getPostalCode(), record));
		} else {
			resultCollector.next(new Pair<String, Location>("00000", record));
		}
	}

}
