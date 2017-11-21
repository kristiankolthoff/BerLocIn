package de.unima.webdataintegration.location.model;

import java.util.Objects;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class AddressXMLReader extends XMLMatchableReader<Address, Attribute>{
	
	@Override
	protected void initialiseDataset(DataSet<Address, Attribute> dataset) {
		super.initialiseDataset(dataset);
		dataset.addAttribute(Address.STREET_ADDRESS);
		dataset.addAttribute(Address.POSTAL_CODE);
		dataset.addAttribute(Address.CITY);
		dataset.addAttribute(Address.COUNTRY);
		dataset.addAttribute(Address.LATITUDE);
		dataset.addAttribute(Address.LONGITUDE);
	}

	@Override
	public Address createModelFromElement(Node node, String provenanceInfo) {
		Address address = new Address();
		address.setStreetAddress(getValueFromChildElement(node, "streetAddress"));
		address.setPostalCode(getValueFromChildElement(node, "postalCode"));
		address.setCity(getValueFromChildElement(node, "city"));
		address.setCountry(getValueFromChildElement(node, "country"));

		//Parse latitude and longitude
		String latitude = getValueFromChildElement(node, "latitude");
		double parsedLatitude = Objects.nonNull(latitude) ? Double.parseDouble(latitude) : 0d;
		String longitude = getValueFromChildElement(node, "longitude");
		double parsedLongitude = Objects.nonNull(longitude) ? Double.parseDouble(longitude) : 0d;
		address.setLatitude(parsedLatitude);
		address.setLongitude(parsedLongitude);
		return (address.getStreetAddress() == null && address.getPostalCode() == null &&
				address.getCity() == null && address.getCountry() == null &&
				address.getLatitude() == 0d && address.getLongitude() == 0d) ? null : address;
	}
	
}
