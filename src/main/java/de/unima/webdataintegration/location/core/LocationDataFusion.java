package de.unima.webdataintegration.location.core;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.fusion.fusers.EMailFuserVoting;
import de.unima.webdataintegration.location.fusion.fusers.LatitudeFuserAverage;
import de.unima.webdataintegration.location.fusion.fusers.LongitudeFuserAverage;
import de.unima.webdataintegration.location.fusion.fusers.NameFuserShortestString;
import de.unima.webdataintegration.location.fusion.fusers.OpeningHoursFuserIntersection;
import de.unima.webdataintegration.location.fusion.fusers.PhoneFuserLongestString;
import de.unima.webdataintegration.location.fusion.fusers.PhotosFuserUnion;
import de.unima.webdataintegration.location.fusion.fusers.PostalCodeFuserMostRecent;
import de.unima.webdataintegration.location.fusion.fusers.PriceFuserAverage;
import de.unima.webdataintegration.location.fusion.fusers.RatingFuserNormalizedAverage;
import de.unima.webdataintegration.location.fusion.fusers.ReviewCountFuserSum;
import de.unima.webdataintegration.location.fusion.fusers.ReviewsFuserUnion;
import de.unima.webdataintegration.location.fusion.fusers.StreetAddressFuserVoting;
import de.unima.webdataintegration.location.fusion.fusers.TypeConcatenationFuser;
import de.unima.webdataintegration.location.fusion.fusers.WebsiteFuserVoting;
import de.unima.webdataintegration.location.fusion.rules.EMailEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.LatitudeEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.LongitudeEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.NameEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.OpeningHoursEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.PhoneEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.PhotosEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.PostalCodeEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.PriceEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.RatingEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.ReviewCountEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.ReviewsEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.StreetAddressEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.TypeEvaluationRule;
import de.unima.webdataintegration.location.fusion.rules.WebsiteEvaluationRule;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.LocationXMLFormatter;
import de.unima.webdataintegration.location.model.LocationXMLReader;

public class LocationDataFusion {

	public static void main(String[] args) throws XPathExpressionException, 
			ParserConfigurationException, SAXException, IOException, TransformerException {
		//Load Prinz dataset
		FusibleHashedDataSet<Location, Attribute> datasetPrinz = new FusibleHashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/input/prinz_locations_with_hours.xml"), 
				"locations/location", datasetPrinz);
		//Load Yelp dataset
		FusibleHashedDataSet<Location, Attribute> datasetYelp = new FusibleHashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/input/yelp_locations.xml"), 
				"locations/location", datasetYelp);
		//Load TripAdvisor dataset
		FusibleHashedDataSet<Location, Attribute> datasetTripadvisor = new FusibleHashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/input/tripadvisor_locations_final.xml"), 
				"locations/location", datasetTripadvisor);
		
		//Print density reports
		datasetPrinz.printDataSetDensityReport();
		datasetYelp.printDataSetDensityReport();
		datasetTripadvisor.printDataSetDensityReport();
		
		//Set provenance scores and dates
		datasetPrinz.setScore(1.0);
		datasetYelp.setScore(3.0);
		datasetTripadvisor.setScore(2.0);

		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern("yyyy-MM-dd")
				.parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				.toFormatter(Locale.ENGLISH);
		
		//Tripadvisor is the most outdated one
		datasetPrinz.setDate(LocalDateTime.parse("2015-01-01", formatter));
		datasetYelp.setDate(LocalDateTime.parse("2015-01-01", formatter));
		datasetTripadvisor.setDate(LocalDateTime.parse("2013-01-01", formatter));
		
		//Load correspondence sets
		CorrespondenceSet<Location, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("src/main/resources/data/correspondences/prinz_yelp_corres.csv"),
				datasetPrinz, datasetYelp);
		correspondences.loadCorrespondences(new File("src/main/resources/data/correspondences/yelp_tripadvisor_corres.csv"),
				datasetYelp, datasetTripadvisor);
		
		//Print correspondence group size distribution
		correspondences.printGroupSizeDistribution();
		
		//Specify data fusion strategy
		DataFusionStrategy<Location, Attribute> strategy = new DataFusionStrategy<>(new LocationXMLReader());
		strategy.addAttributeFuser(Location.NAME, new NameFuserShortestString(), new NameEvaluationRule());
		strategy.addAttributeFuser(Location.PHONE, new PhoneFuserLongestString(), new PhoneEvaluationRule());
		strategy.addAttributeFuser(Location.STREET_ADDRESS, new StreetAddressFuserVoting(), new StreetAddressEvaluationRule());
		strategy.addAttributeFuser(Location.EMAIL, new EMailFuserVoting(), new EMailEvaluationRule());
		strategy.addAttributeFuser(Location.POSTAL_CODE, new PostalCodeFuserMostRecent(), new PostalCodeEvaluationRule());
		strategy.addAttributeFuser(Location.LATITUDE, new LatitudeFuserAverage(), new LatitudeEvaluationRule());
		strategy.addAttributeFuser(Location.LONGITUDE, new LongitudeFuserAverage(), new LongitudeEvaluationRule());
		strategy.addAttributeFuser(Location.RATING, new RatingFuserNormalizedAverage(), new RatingEvaluationRule());
		strategy.addAttributeFuser(Location.REVIEW_COUNT, new ReviewCountFuserSum(), new ReviewCountEvaluationRule());
		strategy.addAttributeFuser(Location.PRICE, new PriceFuserAverage(), new PriceEvaluationRule());
		strategy.addAttributeFuser(Location.PHOTO_URLS, new PhotosFuserUnion(), new PhotosEvaluationRule());
		strategy.addAttributeFuser(Location.REVIEWS, new ReviewsFuserUnion(), new ReviewsEvaluationRule());
		strategy.addAttributeFuser(Location.WEBSITE, new WebsiteFuserVoting(), new WebsiteEvaluationRule());
		strategy.addAttributeFuser(Location.OPENING_HOURS, new OpeningHoursFuserIntersection(), new OpeningHoursEvaluationRule());
		strategy.addAttributeFuser(Location.TYPE, new TypeConcatenationFuser(), new TypeEvaluationRule());
		
		//Run data fusion process
		DataFusionEngine<Location, Attribute> engine = new DataFusionEngine<>(strategy);
		FusibleDataSet<Location, Attribute> fusedDataSet = engine.run(correspondences, null);
		engine.printClusterConsistencyReport(correspondences, null);
		
		new LocationXMLFormatter().writeXML(new File("src/main/resources/data/fusion/fused.xml"), fusedDataSet);
		
		//Load fusion goldstandard
		DataSet<Location, Attribute> goldstandard = new FusibleHashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/goldstandard/fused.xml"), 
				"locations/location", goldstandard);
		//Load fused dataset to initialize attribute schema
		FusibleHashedDataSet<Location, Attribute> fusedFinal = new FusibleHashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/fusion/fused.xml"), 
				"locations/location", fusedFinal);
		//Evaluate data fusion strategy
		DataFusionEvaluator<Location, Attribute> evaluator = new DataFusionEvaluator<>(strategy, 
				new RecordGroupFactory<>());
		evaluator.setVerbose(true);
		try {
		double accuarcy = evaluator.evaluate(fusedFinal, goldstandard, null);
		System.out.println("Accuarcy : " + accuarcy);
		System.out.println("Density of fused dataset");
		fusedFinal.printDataSetDensityReport();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
