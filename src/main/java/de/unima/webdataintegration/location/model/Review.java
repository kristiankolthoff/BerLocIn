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
	
}
