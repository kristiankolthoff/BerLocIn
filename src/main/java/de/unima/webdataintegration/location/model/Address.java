package de.unima.webdataintegration.location.model;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Address extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String streetAddress;
	private String postalCode;
	private String city;
	private String country;
	private double latitude;
	private double longitude;
	
	public Address(String streetAddress, String postalCode, String city, 
			String country, double latitude, double longitude) {
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Address() {}
	
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public static final Attribute STREET_ADDRESS = new Attribute("streetAddress");
	public static final Attribute POSTAL_CODE = new Attribute("postalCode");
	public static final Attribute CITY = new Attribute("city");
	public static final Attribute COUNTRY = new Attribute("country");
	public static final Attribute LATITUDE = new Attribute("latitude");
	public static final Attribute LONGITUDE = new Attribute("longitude");
	
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute == STREET_ADDRESS) return getStreetAddress() != null && !getStreetAddress().isEmpty();
		else if(attribute == POSTAL_CODE) return getPostalCode() != null && !getPostalCode().isEmpty();
		else if(attribute == CITY) return getCity() != null && !getCity().isEmpty();
		else if(attribute == COUNTRY) return getCountry() != null && !getCountry().isEmpty();
		else if(attribute == LATITUDE) return getLatitude() != 0d;
		else if(attribute == LONGITUDE) return getLongitude() != 0d;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
			return false;
		return true;
	}

}