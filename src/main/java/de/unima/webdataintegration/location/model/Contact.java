package de.unima.webdataintegration.location.model;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Contact extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8033763653556986615L;
	private String email;
	private String website;
	private String phone;
	
	public Contact(String email, String website, String phone) {
		this.email = email;
		this.website = website;
		this.phone = phone;
	}
	
	public Contact() {}
	
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
	
	public static final Attribute EMAIL = new Attribute("email");
	public static final Attribute PHONE = new Attribute("phone");
	public static final Attribute WEBSITE = new Attribute("website");
	
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute == EMAIL) return getEmail() != null && !getEmail().isEmpty();
		else if(attribute == PHONE) return getPhone() != null && !getPhone().isEmpty();
		else if(attribute == WEBSITE) return getWebsite() != null && !getWebsite().isEmpty();
		return false;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
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
		Contact other = (Contact) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
			return false;
		return true;
	}

}
