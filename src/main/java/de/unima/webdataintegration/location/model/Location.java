package de.unima.webdataintegration.location.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Location extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3536474009286333329L;

	private String name;
	private String type;
	private Contact contact;
	private Address address;
	private Set<OpeningHours> openingHours;
	private Set<PopularHours> popularHours;
	private double rating;
	private int reviewCount;
	private String price;
	private List<String> photoUrls;
	private District district;
	
	public Location(String identifier, String provenance) {
		super(identifier, provenance);
		this.openingHours = new HashSet<>();
		this.popularHours = new HashSet<>();
		this.photoUrls = new ArrayList<>();
	}
	
	public boolean addOpeningHours(OpeningHours openingHours) {
		return this.openingHours.add(openingHours);
	}
	
	public boolean addPopularHours(PopularHours popularHours) {
		return this.popularHours.add(popularHours);
	}
	
	public boolean addPhotoUrl(String url) {
		return this.photoUrls.add(url);
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}
	
	public Set<OpeningHours> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(Set<OpeningHours> openingHours) {
		this.openingHours = openingHours;
	}

	public Set<PopularHours> getPopularHours() {
		return popularHours;
	}

	public void setPopularHours(Set<PopularHours> popularHours) {
		this.popularHours = popularHours;
	}

	public static final Attribute NAME = new Attribute("name");
	public static final Attribute TYPE = new Attribute("type");
	public static final Attribute CONTACT = new Attribute("contact");
	public static final Attribute ADDRESS = new Attribute("address");
	public static final Attribute OPENING_HOURS = new Attribute("openingHours");
	public static final Attribute POPULAR_HOURS = new Attribute("popularHours");
	public static final Attribute RATING = new Attribute("rating");
	public static final Attribute REVIEW_COUNT = new Attribute("reviewCount");
	public static final Attribute PRICE = new Attribute("price");
	public static final Attribute PHOTO_URLS = new Attribute("photoUrls");
	public static final Attribute DISTRICT = new Attribute("district");

	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute == NAME) return getName() != null && !getName().isEmpty();
		else if(attribute == TYPE) return getType() != null && !getType().isEmpty();
		else if(attribute == CONTACT) return getContact() != null;
		else if(attribute == ADDRESS) return getAddress() != null;
		else if(attribute == OPENING_HOURS) return !getOpeningHours().isEmpty();
		else if(attribute == POPULAR_HOURS) return !getPopularHours().isEmpty();
		else if(attribute == RATING) return getRating() != 0d;
		else if(attribute == REVIEW_COUNT) return getReviewCount() != 0d;
		else if(attribute == PRICE) return getPrice() != null && !getPrice().isEmpty();
		else if(attribute == PHOTO_URLS) return !getPhotoUrls().isEmpty();
		else if(attribute == DISTRICT) return getDistrict() != null;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((openingHours == null) ? 0 : openingHours.hashCode());
		result = prime * result + ((photoUrls == null) ? 0 : photoUrls.hashCode());
		result = prime * result + ((popularHours == null) ? 0 : popularHours.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + reviewCount;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
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
		if (photoUrls == null) {
			if (other.photoUrls != null)
				return false;
		} else if (!photoUrls.equals(other.photoUrls))
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
		return true;
	}

}
