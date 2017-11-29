package de.unima.webdataintegration.location.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.StringUtils;

public class Location extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3536474009286333329L;

	private String name;
	private String type;
	private String email;
	private String website;
	private String phone;
	private String streetAddress;
	private String postalCode;
	private double latitude;
	private double longitude;
	private Set<OpeningHours> openingHours;
	private double rating;
	private int reviewCount;
	private String price;
	private List<String> photoUrls;
	private List<Review> reviews;
	
	public static final List<Pair<String, DayOfWeek>> WEEKDAYS = new ArrayList<>();
	
	static {
		WEEKDAYS.add(new Pair<String, DayOfWeek>("monday", DayOfWeek.MONDAY));
		WEEKDAYS.add(new Pair<String, DayOfWeek>("tuesday", DayOfWeek.TUESDAY));
		WEEKDAYS.add(new Pair<String, DayOfWeek>("wednesday", DayOfWeek.WEDNESDAY));
		WEEKDAYS.add(new Pair<String, DayOfWeek>("thursday", DayOfWeek.THURSDAY));
		WEEKDAYS.add(new Pair<String, DayOfWeek>("friday", DayOfWeek.FRIDAY));
		WEEKDAYS.add(new Pair<String, DayOfWeek>("saturday", DayOfWeek.SATURDAY));
		WEEKDAYS.add(new Pair<String, DayOfWeek>("sunday", DayOfWeek.SUNDAY));
	}
	
	public Location(String identifier, String provenance) {
		super(identifier, provenance);
		this.openingHours = new HashSet<>();
		this.photoUrls = new ArrayList<>();
	}
	
	public boolean addOpeningHours(OpeningHours openingHours) {
		return this.openingHours.add(openingHours);
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


	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
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

	public static final Attribute NAME = new Attribute("name");
	public static final Attribute TYPE = new Attribute("type");
	public static final Attribute EMAIL = new Attribute("email");
	public static final Attribute PHONE = new Attribute("phone");
	public static final Attribute WEBSITE = new Attribute("website");
	public static final Attribute STREET_ADDRESS = new Attribute("streetAddress");
	public static final Attribute POSTAL_CODE = new Attribute("postalCode");
	public static final Attribute LATITUDE = new Attribute("latitude");
	public static final Attribute LONGITUDE = new Attribute("longitude");
	public static final Attribute OPENING_HOURS = new Attribute("openingHours");
	public static final Attribute RATING = new Attribute("rating");
	public static final Attribute REVIEW_COUNT = new Attribute("reviewCount");
	public static final Attribute PRICE = new Attribute("price");
	public static final Attribute PHOTO_URLS = new Attribute("photoUrls");
	public static final Attribute REVIEWS = new Attribute("reviews");

	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute == NAME) return getName() != null && !getName().isEmpty();
		else if(attribute == TYPE) return getType() != null && !getType().isEmpty();
		else if(attribute == EMAIL) return getEmail() != null && !getEmail().isEmpty();
		else if(attribute == PHONE) return getPhone() != null && !getPhone().isEmpty();
		else if(attribute == WEBSITE) return getWebsite() != null && !getWebsite().isEmpty();
		else if(attribute == STREET_ADDRESS) return getStreetAddress() != null && !getStreetAddress().isEmpty();
		else if(attribute == POSTAL_CODE) return getPostalCode() != null && !getPostalCode().isEmpty();
		else if(attribute == LATITUDE) return getLatitude() != 0d;
		else if(attribute == LONGITUDE) return getLongitude() != 0d;
		else if(attribute == OPENING_HOURS) return !getOpeningHours().isEmpty();
		else if(attribute == RATING) return getRating() != 0d;
		else if(attribute == REVIEW_COUNT) return getReviewCount() != 0d;
		else if(attribute == PRICE) return getPrice() != null && !getPrice().isEmpty();
		else if(attribute == PHOTO_URLS) return !getPhotoUrls().isEmpty();
		else if(attribute == REVIEWS) return getReviews() != null && !getReviews().isEmpty();
		return false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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
	
	private Map<Attribute, Collection<String>> provenance = new HashMap<>();
	private Collection<String> recordProvenance;

	public void setRecordProvenance(Collection<String> provenance) {
		recordProvenance = provenance;
	}

	public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}

	public void setAttributeProvenance(Attribute attribute,
			Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	public Collection<String> getAttributeProvenance(Attribute attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((openingHours == null) ? 0 : openingHours.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((photoUrls == null) ? 0 : photoUrls.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + reviewCount;
		result = prime * result + ((reviews == null) ? 0 : reviews.hashCode());
		result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
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
		if (photoUrls == null) {
			if (other.photoUrls != null)
				return false;
		} else if (!photoUrls.equals(other.photoUrls))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
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
		if (reviews == null) {
			if (other.reviews != null)
				return false;
		} else if (!reviews.equals(other.reviews))
			return false;
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
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
