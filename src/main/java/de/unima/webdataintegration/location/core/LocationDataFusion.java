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
import de.unima.webdataintegration.location.fusion.fusers.LongitudeFuserAverage;
import de.unima.webdataintegration.location.fusion.rules.LongitudeEvaluationRule;
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
		strategy.addAttributeFuser(Location.LONGITUDE, new LongitudeFuserAverage(), new LongitudeEvaluationRule());
		
		//Run data fusion process
		DataFusionEngine<Location, Attribute> engine = new DataFusionEngine<>(strategy);
		FusibleDataSet<Location, Attribute> fusedDataSet = engine.run(correspondences, null);
		
		new LocationXMLFormatter().writeXML(new File("src/main/resources/data/fusion/fused.xml"), fusedDataSet);
		
		//Load fusion goldstandard
		DataSet<Location, Attribute> goldstandard = new FusibleHashedDataSet<>();
		new LocationXMLReader().loadFromXML(new File("src/main/resources/data/goldstandard/fused.xml"), 
				"locations/location", goldstandard);
		
		//Evaluate data fusion strategy
		DataFusionEvaluator<Location, Attribute> evaluator = new DataFusionEvaluator<>(strategy, 
				new RecordGroupFactory<>());
		evaluator.setVerbose(true);
		double accuarcy = evaluator.evaluate(fusedDataSet, goldstandard, null);
		System.out.println("Accuarcy : " + accuarcy);
	}
}
