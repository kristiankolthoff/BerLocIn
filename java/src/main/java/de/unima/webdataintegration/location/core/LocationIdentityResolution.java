package de.unima.webdataintegration.location.core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.MatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByArea;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByPostalCode;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByRegion;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByType;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationDistanceAreaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationDistanceComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationEmailUserDomainLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationNameDamerauComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneNameMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneSift4Comparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPostalCodeComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetAddressDistMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetAddressMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetGPSFallbackComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationWebsiteBaseLevenshteinComparator;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.LocationXMLReader;

public class LocationIdentityResolution {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		//Threshold to be used
		final double THRESHOLD = 0.701;
		final MatchingRule<Location, Attribute> MR_1 = matchingRule1(THRESHOLD);
		final MatchingRule<Location, Attribute> MR_2 = matchingRule2(THRESHOLD);
		final MatchingRule<Location, Attribute> MR_3 = matchingRule3(THRESHOLD);
		final MatchingRule<Location, Attribute> MR_4 = matchingRule4(THRESHOLD);
		final MatchingRule<Location, Attribute> MR_5 = matchingRule5(THRESHOLD);
		
		//Blocking key strategies
		final LocationBlockingKeyByPostalCode KEY_POSTAL_CODE = new LocationBlockingKeyByPostalCode();
		final LocationBlockingKeyByRegion KEY_REGION = new LocationBlockingKeyByRegion();
		final LocationBlockingKeyByArea KEY_AREA = new LocationBlockingKeyByArea(52.659811d, 13.071304d, 52.343176d, 13.771915d, 20, 10);
		final LocationBlockingKeyByType KEY_TYPE = new LocationBlockingKeyByType();
		
		//Run identity resolution for Prinz-Tripadvisor
		runIdentityResolution("src/main/resources/data/input/prinz_locations_with_hours.xml", "src/main/resources/data/input/tripadvisor_locations_final.xml", 
				MR_3, "src/main/resources/data/goldstandard/prinz_tripadvisor_gs.csv", KEY_POSTAL_CODE, true, "src/main/resources/data/correspondences/prinz_tripadvisor_corres.csv");

//		//Run identity resolution for Prinz-Yelp
		runIdentityResolution("src/main/resources/data/input/prinz_locations_with_hours.xml", "src/main/resources/data/input/yelp_locations.xml", 
				MR_4, "src/main/resources/data/goldstandard/prinz_yelp_gs.csv", KEY_POSTAL_CODE, true, "src/main/resources/data/correspondences/prinz_yelp_corres.csv");
				
//		//Run identity resolution for Yelp-Tripadvisor
		runIdentityResolution("src/main/resources/data/input/yelp_locations.xml", "src/main/resources/data/input/tripadvisor_locations_final.xml", 
				MR_3, "src/main/resources/data/goldstandard/yelp_tripadvisor_gs.csv", KEY_POSTAL_CODE, true, "src/main/resources/data/correspondences/yelp_tripadvisor_corres.csv");
	}
	
	public static MatchingRule<Location, Attribute> matchingRule1(double threshold) throws Exception {
		LinearCombMatchingRule<Location, Attribute> matchingRule = new LinearCombMatchingRule<>(threshold);
		matchingRule.addComparator(new LocationNameDamerauComparator(), 2.0);
		matchingRule.addComparator(new LocationPostalCodeComparator(), 2.0);
		return matchingRule;
	}
	
	public static MatchingRule<Location, Attribute> matchingRule2(double threshold) throws Exception {
		LinearCombMatchingRule<Location, Attribute> matchingRule = new LinearCombMatchingRule<>(threshold);
		matchingRule.addComparator(new LocationNameDamerauComparator(), 2.0);
		matchingRule.addComparator(new LocationPhoneSift4Comparator(
				LocationPhoneLevenshteinComparator.REGION_DE, false), 2.0);
		matchingRule.addComparator(new LocationEmailUserDomainLevenshteinComparator(), 2.0);
		matchingRule.addComparator(new LocationWebsiteBaseLevenshteinComparator(), 2.0);
		return matchingRule;
	}
	
	public static MatchingRule<Location, Attribute> matchingRule3(double threshold) throws Exception {
		LinearCombMatchingRule<Location, Attribute> matchingRule = new LinearCombMatchingRule<>(threshold);
		matchingRule.addComparator(new LocationNameDamerauComparator(), 2.0);
		matchingRule.addComparator(new LocationPhoneSift4Comparator(
				LocationPhoneLevenshteinComparator.REGION_DE, false), 2.0);
		matchingRule.addComparator(new LocationEmailUserDomainLevenshteinComparator(), 2.0);
		matchingRule.addComparator(new LocationStreetAddressMetaComparator(1), 2.0);
		return matchingRule;
	}
	
	public static MatchingRule<Location, Attribute> matchingRule4(double threshold) throws Exception {
		LinearCombMatchingRule<Location, Attribute> matchingRule = new LinearCombMatchingRule<>(threshold);
		matchingRule.addComparator(new LocationNameDamerauComparator(), 2.0);
		matchingRule.addComparator(new LocationPhoneSift4Comparator(
				LocationPhoneLevenshteinComparator.REGION_DE, false), 2.0);
		matchingRule.addComparator(new LocationEmailUserDomainLevenshteinComparator(), 2.0);
		matchingRule.addComparator(new LocationStreetAddressDistMetaComparator(1), 2.0);
		return matchingRule;
	}
	
	public static MatchingRule<Location, Attribute> matchingRule5(double threshold) throws Exception {
		LinearCombMatchingRule<Location, Attribute> matchingRule = new LinearCombMatchingRule<>(threshold);
		matchingRule.addComparator(new LocationNameDamerauComparator(), 2.0);
		matchingRule.addComparator(new LocationDistanceComparator(300), 2.0);
		matchingRule.addComparator(new LocationPhoneLevenshteinComparator(
				LocationPhoneLevenshteinComparator.REGION_DE, false), 2.0);
		matchingRule.addComparator(new LocationPhoneSift4Comparator(
				LocationPhoneLevenshteinComparator.REGION_DE, false), 2.0);
		matchingRule.addComparator(new LocationStreetAddressDistMetaComparator(1), 2.0);
		matchingRule.addComparator(new LocationStreetAddressMetaComparator(1), 2.0);
		matchingRule.addComparator(new LocationPhoneNameMetaComparator(), 2);
		matchingRule.addComparator(new LocationPostalCodeComparator(), 1.0);
		matchingRule.addComparator(new LocationEmailUserDomainLevenshteinComparator(), 2);
		matchingRule.addComparator(new LocationWebsiteBaseLevenshteinComparator(), 2);
		matchingRule.addComparator(new LocationStreetGPSFallbackComparator(0.6, 
				new LocationStreetAddressMetaComparator(1), new LocationDistanceAreaComparator(200)), 2);
		return matchingRule;
	}
	
	public static void runIdentityResolution(String datasetPath1, String datasetPath2,
			MatchingRule<Location, Attribute> matchingRule, String goldstandardPath, 
			RecordBlockingKeyGenerator<Location, Attribute> blockingKey, boolean standard, String outputPath) 
					throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
		//Load datasets
		DataSet<Location, Attribute> dataset1 = new HashedDataSet<>();
		DataSet<Location, Attribute> dataset2 = new HashedDataSet<>();
		LocationXMLReader readerFirst = new LocationXMLReader();
		readerFirst.loadFromXML(new File(datasetPath1), 
				"locations/location", dataset1);
		LocationXMLReader readerSecond = new LocationXMLReader();
		readerSecond.loadFromXML(new File(datasetPath2), 
				"locations/location", dataset2);
		System.out.println("DateTime ignores first dataset = from : " + readerFirst.getFromReadingIgnores() 
							+ ", to : " + readerFirst.getToReadingIgnores());
		System.out.println("DateTime ignores second dataset = from : " + readerSecond.getFromReadingIgnores() 
		+ ", to : " + readerSecond.getToReadingIgnores());
		//Blocker and engine
		StandardRecordBlocker<Location, Attribute> blocker = new StandardRecordBlocker<>(blockingKey);
		MatchingEngine<Location, Attribute> matchingEngine = new MatchingEngine<>();
		//Identity resolution
		Processable<Correspondence<Location,Attribute>> correspondences = 
				matchingEngine.runIdentityResolution(dataset1, dataset2, null, matchingRule, blocker);
		//Write correspondences
		new CSVCorrespondenceFormatter().writeCSV(new File(outputPath),
				correspondences);
		//Load goldstandard
		MatchingGoldStandard goldstandard = new MatchingGoldStandard();
		goldstandard.loadFromCSVFile(new File(goldstandardPath));
		//Evaluate results
		MatchingEvaluator<Location, Attribute> evaluator = new MatchingEvaluator<>(true);
		Performance performance = evaluator.evaluateMatching(correspondences.get(), goldstandard);
		//Print performance
		System.out.println(dataset1.getRandomRecord().getProvenance() + " <-> " 
					+ dataset2.getRandomRecord().getProvenance());
		System.out
				.println(String.format(
						"Precision: %.4f\nRecall: %.4f\nF1: %.4f",
						performance.getPrecision(), performance.getRecall(),
						performance.getF1()));
	}
	
}
