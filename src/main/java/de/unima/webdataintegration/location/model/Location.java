package de.unima.webdataintegration.location.model;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Location extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3536474009286333329L;

	private String name;
	private String type;
	private String email;
	private String openingHours;
	private String website;
	private String popularHours;
	private double latitude;
	private double longitude;
	private double rating;
	private int reviewCount;
	private String phone;
	private String address;
	private String price;
	private District district;
	
	public Location(String identifier, String provenance) {
		super(identifier, provenance);
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

	public static final Attribute NAME = new Attribute("name");
	public static final Attribute TYPE = new Attribute("type");
	public static final Attribute LATITUDE = new Attribute("latitude");
	public static final Attribute LONGITUDE = new Attribute("longitude");
	public static final Attribute PHONE = new Attribute("phone");

	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute.getIdentifier().equals("name")) return getName() != null; 
		else if(attribute.getIdentifier().equals("latitude")) return getLatitude() != 0d;
		else if(attribute.getIdentifier().equals("longitude")) return getLongitude() != 0d;
		else if(attribute.getIdentifier().equals("phone")) return getPhone() != null;
		return false;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (openingHours == null) {
			if (other.openingHours != null)
				return false;
		} else if (!openingHours.equals(other.openingHours))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (popularHours == null) {
			if (other.popularHours != null)
				return false;
		} else if (!popularHours.equals(other.popularHours))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
			return false;
		if (reviewCount != other.reviewCount)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
			return false;
		return true;
	}
	
	

}
