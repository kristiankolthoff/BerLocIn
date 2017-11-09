package de.unima.webdataintegration.location.model;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class District extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4847960426531104385L;
	
	private String name;
	private double areaTotal;
	private String administrativeDistrict;
	private String areaCode;
	private int population;
	private double latitude;
	private double longitude;
	private int incidents;
	private int hz;
	
	public District(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getAreaTotal() {
		return areaTotal;
	}


	public void setAreaTotal(double areaTotal) {
		this.areaTotal = areaTotal;
	}


	public String getAdministrativeDistrict() {
		return administrativeDistrict;
	}


	public void setAdministrativeDistrict(String administrativeDistrict) {
		this.administrativeDistrict = administrativeDistrict;
	}


	public String getAreaCode() {
		return areaCode;
	}


	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}


	public int getPopulation() {
		return population;
	}


	public void setPopulation(int population) {
		this.population = population;
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


	public int getIncidents() {
		return incidents;
	}


	public void setIncidents(int incidents) {
		this.incidents = incidents;
	}


	public int getHz() {
		return hz;
	}


	public void setHz(int hz) {
		this.hz = hz;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static final Attribute NAME = new Attribute("name");
	public static final Attribute AREA_CODE = new Attribute("areaCode");

	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute == NAME) return getName() != null;
		else if(attribute == AREA_CODE) return getAreaCode() !=  null;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((administrativeDistrict == null) ? 0 : administrativeDistrict.hashCode());
		result = prime * result + ((areaCode == null) ? 0 : areaCode.hashCode());
		long temp;
		temp = Double.doubleToLongBits(areaTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + hz;
		result = prime * result + incidents;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + population;
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
		District other = (District) obj;
		if (administrativeDistrict == null) {
			if (other.administrativeDistrict != null)
				return false;
		} else if (!administrativeDistrict.equals(other.administrativeDistrict))
			return false;
		if (areaCode == null) {
			if (other.areaCode != null)
				return false;
		} else if (!areaCode.equals(other.areaCode))
			return false;
		if (Double.doubleToLongBits(areaTotal) != Double.doubleToLongBits(other.areaTotal))
			return false;
		if (hz != other.hz)
			return false;
		if (incidents != other.incidents)
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
		if (population != other.population)
			return false;
		return true;
	}
	
	

}
