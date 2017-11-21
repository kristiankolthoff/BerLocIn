package de.unima.webdataintegration.location.identityresolution;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.Test;

import de.unima.webdataintegration.location.identityresolution.blockingkeys.LocationBlockingKeyByRegion;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationPhoneLevenshteinComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationStreetAddressMetaComparator;
import de.unima.webdataintegration.location.identityresolution.comparators.LocationWebsiteBaseLevenshteinComparator;
import de.unima.webdataintegration.location.model.Address;
import de.unima.webdataintegration.location.model.Contact;
import de.unima.webdataintegration.location.model.Location;

public class LocationPhoneComparatorsTest {

	@Test
	public void locationPhoneLevenshteinComparatorTest() {
		LocationPhoneLevenshteinComparator phoneComp = new LocationPhoneLevenshteinComparator();
		Location location1 = new Location("", "");
		location1.setContact(new Contact("", "", "+4974592000"));
		Location location2 = new Location("", "");
		location2.setContact(new Contact("", "", "+49731920011"));
		double similarity = phoneComp.compare(location1, location2, null);
	}
	
	@Test
	public void locationWebsiteBaseLevenshteinComparatorTest() {
		LocationWebsiteBaseLevenshteinComparator phoneComp = new LocationWebsiteBaseLevenshteinComparator();
		Location location1 = new Location("", "");
		location1.setContact(new Contact("", "www.example.com", ""));
		Location location2 = new Location("", "");
		location2.setContact(new Contact("", "www.example2.de", ""));
		double similarity = phoneComp.compare(location1, location2, null);
		System.out.println(similarity);
	}
	
	@Test
	public void locationStreetAddressMetaComparatorTest() {
		LocationStreetAddressMetaComparator phoneComp = new LocationStreetAddressMetaComparator(2, 1);
		Location location1 = new Location("", "");
		location1.setAddress(new Address("Hannoversche Str. 19", "", "", "", 0, 0));
		Location location2 = new Location("", "");
		location2.setAddress(new Address("Gabriele-Tergit-Promenade 17", "", "", "", 0, 0));
		double similarity = phoneComp.compare(location1, location2, null);
		System.out.println(similarity);
	}
	
	@Test
	public void blockingKeyGeneratorRegionMapTest() throws IOException {
		LocationBlockingKeyByRegion blockingKey = new LocationBlockingKeyByRegion();
	}
}
