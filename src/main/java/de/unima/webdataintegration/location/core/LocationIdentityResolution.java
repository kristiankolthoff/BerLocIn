package de.unima.webdataintegration.location.core;

import java.io.File;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByPostalCode;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByRegion;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationDistanceComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationNameLevenshteinComparator;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.LocationXMLReader;

public class LocationIdentityResolution {

	public static void main(String[] args) throws Exception {
		//Load all the data sets and prepare for identity resolution
		DataSet<Location, Attribute> prinzLocations = new HashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/prinz_locations.xml"), 
				"locations/location", prinzLocations);
		DataSet<Location, Attribute> yelpLocations = new HashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/yelp_locations.xml"), 
				"locations/location", yelpLocations);
		
		//Create linear combination matching rule
		LinearCombinationMatchingRule<Location, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.9);
		matchingRule.addComparator(new LocationNameLevenshteinComparator(), 1.0);
//		matchingRule.addComparator(new LocationDistanceComparator(100), 1.0);
		
		StandardRecordBlocker<Location, Attribute> blocker = 
//				new StandardRecordBlocker<>(new LocationBlockingKeyByPostalCode());
				new StandardRecordBlocker<>(new LocationBlockingKeyByRegion());
		
		MatchingEngine<Location, Attribute> matchingEngine = new MatchingEngine<>();
		
		Processable<Correspondence<Location,Attribute>> correspondences = 
				matchingEngine.runIdentityResolution(prinzLocations, yelpLocations, null, matchingRule, blocker);
		
		new CSVCorrespondenceFormatter().writeCSV(new File("src/main/resources/data/results/prinz_yelp_corres.csv"),
				correspondences);
	}
}
