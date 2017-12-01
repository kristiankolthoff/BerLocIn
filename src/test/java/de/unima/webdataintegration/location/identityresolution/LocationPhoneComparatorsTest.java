package de.unima.webdataintegration.location.identityresolution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.junit.Test;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.fusion.rules.OpeningHoursEvaluationRule;
import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByRegion;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationDistanceAreaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationDistanceComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationEmailUserDomainLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneSift4Comparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetAddressMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationWebsiteBaseLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationWebsiteDamerauComparator;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.OpeningHours;
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
		System.out.println(preprocess("https://alkmaar.fc-bayern-de/home/de"));
		LocationDistanceComparator comp = new LocationDistanceComparator(100);
		LocationDistanceAreaComparator compArea = new LocationDistanceAreaComparator(10);
		LocationEmailUserDomainLevenshteinComparator compEmail = new LocationEmailUserDomainLevenshteinComparator();
		LocationWebsiteDamerauComparator compWebsiteComplete = new LocationWebsiteDamerauComparator();
		LocationWebsiteBaseLevenshteinComparator compWebsite = new LocationWebsiteBaseLevenshteinComparator();
		LocationPhoneLevenshteinComparator compPhone = 
				new LocationPhoneLevenshteinComparator(LocationPhoneLevenshteinComparator.REGION_DE, false);
		LocationPhoneSift4Comparator compPhoneSift =
				new LocationPhoneSift4Comparator(LocationPhoneLevenshteinComparator.REGION_DE, false);
		Location record1 = new Location("", "");
		record1.setLatitude(52.497092);
		record1.setLongitude(13.322398);
		record1.setName("Cafe Anna Blume");
		record1.setEmail("infadmin@novus-mannheim.de");
		record1.setWebsite("http://novus-mannheim.de/home");
		record1.setPhone("+49 30 28776780");
		record1.setPostalCode("10997");
		record1.setStreetAddress("Muskauerstrasse 1 10997 Berlin Germany");
		Location record2 = new Location("", "");
		record2.setLatitude(52.497179);
		record2.setLongitude(13.322637);
		record2.setEmail("info@novus-mannheim.de");
		record2.setWebsite("https://www.novus-mannheim.de");
		record2.setPhone("0/30/281467780");
		record2.setStreetAddress("Muskauer Str. 1");
		record2.setName("Anna Blume");
		System.out.println("Similarity lat,long: " + comp.compare(record1, record2, null) + ", " 
					+ compArea.compare(record1, record2, null));
		System.out.println("Sim Email : " + compEmail.compare(record1, record2, null) + ", " 
				+ new LevenshteinSimilarity().calculate(record1.getEmail(), record2.getEmail()));
		System.out.println("Sim website : " + compWebsiteComplete.compare(record1, record2, null) + ", "
				+ compWebsite.compare(record1, record2, null));
		System.out.println("Sim phone : " + compPhone.compare(record1, record2, null) + ", " +
				compPhoneSift.compare(record1, record2, null));
		LocationStreetAddressMetaComparator compStreet = new LocationStreetAddressMetaComparator(1);
		System.out.println("Street : " + compStreet.compare(record1, record2, null));
		record1.setPostalCode("10997");
		record1.setStreetAddress("Im Ullsteinhaus Mariendorfer Damm 1");
		record2.setStreetAddress("Mariendorfer Damm 1 Im Ullsteinhaus");
		record2.setName("Anna Blume");
		System.out.println("Street : " + compStreet.compare(record1, record2, null));
		record1.setPostalCode("10997");
		record1.setStreetAddress("Mariannenstraße 43");
		record2.setStreetAddress("Mariannenstr. 43");
		record2.setName("Anna Blume");
		System.out.println("Street : " + compStreet.compare(record1, record2, null));
	}
	
	@Test
	public void testOpeningHoursEquality() {
		DateTimeFormatter germanFormatter = DateTimeFormatter
				.ofLocalizedTime(FormatStyle.SHORT)
				.withLocale(Locale.GERMAN);
		OpeningHoursEvaluationRule evaluationRule = new OpeningHoursEvaluationRule();
		Location record1 = new Location("", "");
		record1.addOpeningHours(new OpeningHours(DayOfWeek.FRIDAY, LocalTime.parse("17:00", germanFormatter), LocalTime.parse("21:30", germanFormatter)));
		record1.addOpeningHours(new OpeningHours(DayOfWeek.SATURDAY, LocalTime.parse("17:00", germanFormatter), LocalTime.parse("21:30", germanFormatter)));
		Location record2 = new Location("", "");
		record2.addOpeningHours(new OpeningHours(DayOfWeek.FRIDAY, LocalTime.parse("17:00", germanFormatter), LocalTime.parse("21:30", germanFormatter)));
		record2.addOpeningHours(new OpeningHours(DayOfWeek.SATURDAY, LocalTime.parse("17:00", germanFormatter), LocalTime.parse("21:30", germanFormatter)));
		System.out.println(evaluationRule.isEqual(record1, record2, (Attribute)null));
	}
	
	public String preprocess(String website) {
		try {
			URI uri = new URI(website);
			String hostname = uri.getHost();
			if (hostname != null) {
			      return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
			}
			return hostname;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
