package de.unima.webdataintegration.location.identityresolution.blockingkeys;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;

public class LocationBlockingKeyByRegion extends RecordBlockingKeyGenerator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private Map<String, String> postalCode2Region;

	
	public LocationBlockingKeyByRegion() throws IOException {
		this.postalCode2Region = initializeRegionMap();
	}


	private Map<String, String> initializeRegionMap() throws IOException {
		BufferedReader reader = Files.newBufferedReader(
				Paths.get("src/main/resources/maps/postalCode2District.csv"), Charset.forName("ISO-8859-1"));
		Map<String, String> map = reader.lines().collect(Collectors.
				toMap(s-> {return s.split(",")[0];}, s-> {return s.split(",")[1];}));
		return map;
	}


	@Override
	public void generateBlockingKeys(Location record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Location>> resultCollector) {
		if(Objects.nonNull(record.getPostalCode())) {
			resultCollector.next(new Pair<String, Location>(postalCode2Region.get(record.getPostalCode()), record));
		} else {
			resultCollector.next(new Pair<String, Location>("00000", record));
		}
	}

}
