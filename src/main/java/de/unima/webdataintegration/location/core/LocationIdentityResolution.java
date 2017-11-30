package de.unima.webdataintegration.location.core;

import java.io.File;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByName;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByPostalCode;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByRegion;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByType;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationDistanceComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationEmailUserDomainLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationNameDamerauComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneNameMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPostalCodeComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetAddressLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetAddressMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationWebsiteBaseLevenshteinComparator;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.LocationXMLReader;

public class LocationIdentityResolution {

	public static void main(String[] args) throws Exception {
		//Load all the data sets and prepare for identity resolution
		DataSet<Location, Attribute> prinzLocations = new HashedDataSet<>();
		DataSet<Location, Attribute> yelpLocations = new HashedDataSet<>();
		LocationXMLReader readerFirst = new LocationXMLReader();
		readerFirst.loadFromXML(new File("src/main/resources/data/input/prinz_locations_with_hours.xml"), 
				"locations/location", yelpLocations);
		LocationXMLReader readerSecond = new LocationXMLReader();
		readerSecond.loadFromXML(new File("src/main/resources/data/input/tripadvisor_locations_final.xml"), 
				"locations/location", prinzLocations);
		System.out.println("DateTime ignores first dataset = from : " + readerFirst.getFromReadingIgnores() 
							+ ", to : " + readerFirst.getToReadingIgnores());
		System.out.println("DateTime ignores second dataset = from : " + readerSecond.getFromReadingIgnores() 
		+ ", to : " + readerSecond.getToReadingIgnores());
		
		
		//Create linear combination matching rule
		LinearCombMatchingRule<Location, Attribute> matchingRule = new LinearCombMatchingRule<>(0.709);
		matchingRule.addComparator(new LocationNameDamerauComparator(), 2.0);
//		matchingRule.addComparator(new LocationDistanceComparator(700), 2.5);
		matchingRule.addComparator(new LocationPhoneLevenshteinComparator(
				LocationPhoneLevenshteinComparator.REGION_DE, false), 2.0);
//		matchingRule.addComparator(new LocationStreetAddressMetaComparator(1), 2.0);
//		matchingRule.addComparator(new LocationPhoneNameMetaComparator(), 2);
//		matchingRule.addComparator(new LocationPostalCodeComparator(), 1.0);
//		matchingRule.addComparator(new LocationEmailUserDomainLevenshteinComparator(), 2);
//		matchingRule.addComparator(new LocationWebsiteBaseLevenshteinComparator(), 2);
		
		
		StandardRecordBlocker<Location, Attribute> blocker = 
				new StandardRecordBlocker<>(new LocationBlockingKeyByPostalCode());
//				new StandardRecordBlocker<>(new LocationBlockingKeyByRegion());
//				new StandardRecordBlocker<>(new LocationBlockingKeyByType());
//				new StandardRecordBlocker<>(new LocationBlockingKeyByName());
		
		MatchingEngine<Location, Attribute> matchingEngine = new MatchingEngine<>();
		
		Processable<Correspondence<Location,Attribute>> correspondences = 
				matchingEngine.runIdentityResolution(yelpLocations, prinzLocations, null, matchingRule, blocker);
		
		new CSVCorrespondenceFormatter().writeCSV(new File("src/main/resources/data/results/prinz_tripadvisor_similar_name_diff_address_corres_2.csv"),
				correspondences);
		
		MatchingGoldStandard goldstandard = new MatchingGoldStandard();
		goldstandard.loadFromCSVFile(new File("src/main/resources/data/goldstandard/prinz_tripadvisor_gs.csv"));
		
		MatchingEvaluator<Location, Attribute> evaluator = new MatchingEvaluator<>(true);
		Performance performance = evaluator.evaluateMatching(correspondences.get(), goldstandard);
		
		System.out.println("Prinz <-> Yelp");
		System.out
				.println(String.format(
						"Precision: %.4f\nRecall: %.4f\nF1: %.4f",
						performance.getPrecision(), performance.getRecall(),
						performance.getF1()));
		
//		String options[] = new String[] { "" };
//		String modelType = "SimpleLogistic"; // use a logistic regression
//		WekaMatchingRule<Location, Attribute> matchingRuleLearned
//		= new WekaMatchingRule<>(0.81, modelType, options);
//		// add comparators
//		matchingRuleLearned.addComparator(new LocationNameLevenshteinComparator());
//		matchingRuleLearned.addComparator(new LocationPhoneLevenshteinComparator());
//		matchingRuleLearned.addComparator(new LocationStreetAddressLevenshteinComparator());
//		matchingRuleLearned.addComparator(new LocationPostalCodeComparator());
//		// load the training set
//		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
//		gsTraining.loadFromCSVFile(new File("src/main/resources/data/goldstandard/yelp_tripadvisor_gs.csv"));
//		// train the matching rule's model
//		RuleLearner<Location, Attribute> learner = new RuleLearner<>();
//		learner.learnMatchingRule(prinzLocations, yelpLocations, null, matchingRuleLearned,
//		gsTraining);
//		matchingRuleLearned.storeModel(new File("src/main/resources/data/modelRule.txt"));
//		System.out.println(matchingRuleLearned);
//		System.out.println();
	}
	
}
