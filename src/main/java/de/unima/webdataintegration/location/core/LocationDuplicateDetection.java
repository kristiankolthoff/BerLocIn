package de.unima.webdataintegration.location.core;

import java.io.File;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.StaticBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.LocationXMLReader;

public class LocationDuplicateDetection {

	public static void main(String[] args) throws Exception {
		//Load all the data sets and prepare for identity resolution
		DataSet<Location, Attribute> locationsYelp = new HashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/prinz_locations.xml"), 
				"locations/location", locationsYelp);
		
		//Create linear combination matching rule
		LinearCombinationMatchingRule<Location, Attribute> matchingRule = 
				new LinearCombinationMatchingRule<>(0.5);
		
		StandardRecordBlocker<Location, Attribute> blocker = 
				new StandardRecordBlocker<>(new StaticBlockingKeyGenerator<>());
		
		MatchingEngine<Location, Attribute> matchingEngine =
				new MatchingEngine<>();
		
		Processable<Correspondence<Location,Attribute>> correspondences = 
				matchingEngine.runDuplicateDetection(locationsYelp, matchingRule, blocker);
		
		new CSVCorrespondenceFormatter().writeCSV(new File("src/main/resources/data/results/prinz_dupl.csv"),
				correspondences);
	}
}
