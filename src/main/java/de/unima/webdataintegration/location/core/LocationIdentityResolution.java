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
import de.unima.webdataintegration.location.identityresolution.LocationBlockingKeyByType;
import de.unima.webdataintegration.location.identityresolution.LocationDistanceComparator;
import de.unima.webdataintegration.location.identityresolution.LocationNameComparator;
import de.unima.webdataintegration.location.identityresolution.LocationPhoneComparator;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.LocationXMLReader;

public class LocationIdentityResolution {

	public static void main(String[] args) throws Exception {
		//Load all the data sets and prepare for identity resolution
		DataSet<Location, Attribute> locationsYelp = new HashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/locations_yelp_3.xml"), 
				"locations/location", locationsYelp);
		DataSet<Location, Attribute> locationsYelp2 = new HashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/locations_yelp_small.xml"), 
				"locations/location", locationsYelp2);
		
		//Create linear combination matching rule
		LinearCombinationMatchingRule<Location, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.5);
		matchingRule.addComparator(new LocationNameComparator(), 0.000000001);
		matchingRule.addComparator(new LocationDistanceComparator(), 1.0);
		matchingRule.addComparator(new LocationPhoneComparator(), 0.8);
		
		StandardRecordBlocker<Location, Attribute> blocker = 
				new StandardRecordBlocker<>(new LocationBlockingKeyByType());
		
		MatchingEngine<Location, Attribute> matchingEngine =
				new MatchingEngine<>();
		
		Processable<Correspondence<Location,Attribute>> correspondences = 
				matchingEngine.runIdentityResolution(locationsYelp, locationsYelp2, null, matchingRule, blocker);
		
		new CSVCorrespondenceFormatter().writeCSV(new File("src/main/resources/data/results/yelp_dupl.csv"),
				correspondences);
	}
}
