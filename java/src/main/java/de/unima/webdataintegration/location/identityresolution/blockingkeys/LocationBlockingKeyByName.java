package de.unima.webdataintegration.location.identityresolution.blockingkeys;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;

public class LocationBlockingKeyByName extends RecordBlockingKeyGenerator<Location, Attribute>{

	private static final long serialVersionUID = -4695154257677561126L;

	@Override
	public void generateBlockingKeys(Location record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Location>> resultCollector) {
		resultCollector.next(new Pair<String, Location>(Character.toString(record.getName().toLowerCase().charAt(0)), 
				record));
	}

}
