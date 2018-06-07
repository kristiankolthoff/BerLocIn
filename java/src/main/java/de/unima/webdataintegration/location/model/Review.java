package de.unima.webdataintegration.location.model;

import java.io.Serializable;
import java.time.LocalDate;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Review extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4847960426531104385L;
	
	private String userName;
	private String userImage;
	private String heading;
	private double rating;
	private String content;
	private LocalDate date;

	
	public Review(String identifier, String provenance) {
		super(identifier, provenance);
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public static final Attribute USER_NAME = new Attribute("username");
	public static final Attribute USER_IMG = new Attribute("userimg");
	public static final Attribute HEADING = new Attribute("heading");
	public static final Attribute RATING = new Attribute("rating");
	public static final Attribute CONTENT = new Attribute("content");
	public static final Attribute DATE = new Attribute("date");

	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute == USER_NAME) return getUserName() != null && !getUserName().isEmpty();
		else if(attribute == USER_IMG) return getUserImage() != null && !getUserImage().isEmpty();
		else if(attribute == HEADING) return getHeading() != null && !getHeading().isEmpty();
		else if(attribute == RATING) return getRating() != 0d;
		else if(attribute == CONTENT) return getContent() != null && !getContent().isEmpty();
		else if(attribute == DATE) return getDate() != null;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((heading == null) ? 0 : heading.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((userImage == null) ? 0 : userImage.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Review other = (Review) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (heading == null) {
			if (other.heading != null)
				return false;
		} else if (!heading.equals(other.heading))
			return false;
		if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
			return false;
		if (userImage == null) {
			if (other.userImage != null)
				return false;
		} else if (!userImage.equals(other.userImage))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
