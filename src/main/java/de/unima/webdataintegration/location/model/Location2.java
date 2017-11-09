package de.unima.webdataintegration.location.model;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.plugin.annotations.AttributeValue;
import de.unima.webdataintegration.location.plugin.annotations.Ignore;
import de.unima.webdataintegration.location.plugin.model.StandardAbstractRecord;

public class Location2 extends StandardAbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3536474009286333329L;

	@AttributeValue(name="name")
	private String name;
	
	@AttributeValue(name = "type")
	private String type;
	private String email;
	private String openingHours;
	private String website;
	private String popularHours;
	
	@AttributeValue(name = "latitude")
	private double latitude;
	
	@AttributeValue(name = "longitude")
	private double longitude;
	private double rating;
	private int reviewCount;
	
	@AttributeValue(name = "phone")
	@Ignore
	private String phone;
	private String address;
	private String price;
	private District district;
	
	public Location2(String identifier, String provenance) {
		super(identifier, provenance, Attribute.class);
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getOpeningHours() {
		return openingHours;
	}


	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}


	public String getWebsite() {
		return website;
	}


	public void setWebsite(String website) {
		this.website = website;
	}


	public String getPopularHours() {
		return popularHours;
	}


	public void setPopularHours(String popularHours) {
		this.popularHours = popularHours;
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


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public int getReviewCount() {
		return reviewCount;
	}


	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public District getDistrict() {
		return district;
	}


	public void setDistrict(District district) {
		this.district = district;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((openingHours == null) ? 0 : openingHours.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((popularHours == null) ? 0 : popularHours.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + reviewCount;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
		return result;
	}

}
