package de.unima.webdataintegration.location.identityresolution;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.junit.Test;

import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByRegion;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetAddressMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationWebsiteBaseLevenshteinComparator;
import de.unima.webdataintegration.location.model.Location;
import info.debatty.java.stringsimilarity.Damerau;

public class LocationPhoneComparatorsTest {

//	@Test
//	public void locationPhoneLevenshteinComparatorTest() {
//		LocationPhoneLevenshteinComparator phoneComp = new LocationPhoneLevenshteinComparator();
//		Location location1 = new Location("", "");
//		double similarity = phoneComp.compare(location1, location2, null);
//	}
//	
//	@Test
//	public void locationWebsiteBaseLevenshteinComparatorTest() {
//		LocationWebsiteBaseLevenshteinComparator phoneComp = new LocationWebsiteBaseLevenshteinComparator();
//		Location location1 = new Location("", "");
//		location1.setContact(new Contact("", "www.example.com", ""));
//		Location location2 = new Location("", "");
//		location2.setContact(new Contact("", "www.example2.de", ""));
//		double similarity = phoneComp.compare(location1, location2, null);
//		System.out.println(similarity);
//	}
//	
//	@Test
//	public void locationStreetAddressMetaComparatorTest() {
//		LocationStreetAddressMetaComparator phoneComp = new LocationStreetAddressMetaComparator(2, 1);
//		Location location1 = new Location("", "");
//		location1.setAddress(new Address("Hannoversche Str. 19", "", "", "", 0, 0));
//		Location location2 = new Location("", "");
//		location2.setAddress(new Address("Gabriele-Tergit-Promenade 17", "", "", "", 0, 0));
//		double similarity = phoneComp.compare(location1, location2, null);
//		System.out.println(similarity);
//	}
//	
//	@Test
//	public void blockingKeyGeneratorRegionMapTest() throws IOException {
//		LocationBlockingKeyByRegion blockingKey = new LocationBlockingKeyByRegion();
//	}
	
	@Test
	public void dateTest() {
		String date = "21. August 2017";
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		        .appendPattern("d. MMMM yyyy")
		        .toFormatter(Locale.GERMAN);
		LocalDate dateParsed = LocalDate.parse(date, formatter);
		System.out.println(dateParsed.format(formatter));
	}
	
	@Test
	public void test() {
		Damerau sim = new Damerau();
		String s1 = "Kollwitzstr. 83";
		String s2 = "Kollwitzstrasse 83";
		double dist = sim.distance(s1, s2);
		double max = Math.max(s1.length(), s2.length());
		double realSim = 1 - dist / max;
		System.out.println(realSim);
	}
}
