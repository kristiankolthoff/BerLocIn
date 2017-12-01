package de.unima.webdataintegration.location.identityresolution.blockingkeys;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;

public class LocationBlockingKeyByType extends RecordBlockingKeyGenerator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private Map<String, String> mapYelp;
	private Map<String, String> mapPrinz;
	
	
	public LocationBlockingKeyByType() throws IOException {
		initializeRegionMap();
	}

	private void initializeRegionMap() throws IOException {
		BufferedReader reader = Files.newBufferedReader(
				Paths.get("src/main/resources/maps/yelp_mapping.txt"), Charset.forName("UTF-8"));
		mapYelp = reader.lines().filter(s -> {return !s.isEmpty();}).collect(Collectors.
				toMap(s-> {return s.split(";")[0];}, s-> {return s.split(";")[1];}));
		reader = Files.newBufferedReader(
				Paths.get("src/main/resources/maps/prinz_mapping.txt"), Charset.forName("UTF-8"));
		mapPrinz = reader.lines().filter(s -> {return !s.isEmpty();}).collect(Collectors.
				toMap(s-> {return s.split(";")[0];}, s-> {return s.split(";")[1];}));
	}
	
	@Override
	public void generateBlockingKeys(Location record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Location>> resultCollector) {
		if(record.getProvenance().contains("yelp")) {
			String yelpKey = mapYelp.containsKey(record.getType()) ? 
					mapYelp.get(record.getType()) : "misc";
			resultCollector.next(new Pair<String, Location>(yelpKey, record));
		} else if(record.getProvenance().contains("prinz")) {
			try {
			String prinzKey = prinzLookup(record.getType());
			resultCollector.next(new Pair<String, Location>(prinzKey, record));
			}catch(NullPointerException e) {
				System.out.println();
			}
		}
		resultCollector.next(new Pair<>(record.getType(), record));
	}
	
	private String prinzLookup(String prinzCategories) {
		String[] categories = prinzCategories.split(",");
		String key = "misc";
		for (int i = categories.length-1; i >= 0; i--) {
			if(mapPrinz.containsKey(categories[i].replaceAll("&amp;", "&").trim())) {
				return mapPrinz.get(categories[i]);
			}
		}
		return key;
	}
}
