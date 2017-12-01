package de.unima.webdataintegration.location.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class OpeningHours extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7631292304110881126L;
	private DayOfWeek dayOfWeek;
	private LocalTime from;
	private LocalTime to;
	
	public OpeningHours(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
		this.dayOfWeek = dayOfWeek;
		this.from = from;
		this.to = to;
	}
	
	public OpeningHours() {}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalTime getFrom() {
		return from;
	}

	public void setFrom(LocalTime from) {
		this.from = from;
	}

	public LocalTime getTo() {
		return to;
	}

	public void setTo(LocalTime to) {
		this.to = to;
	}
	
	@Override
	public boolean hasValue(Attribute attribute) {
		return false;
	}
	
	public static boolean equalFromAndTo(OpeningHours open1, OpeningHours open2) {
		LocalTime from1 = open1.getFrom();
		LocalTime from2 = open2.getFrom();
		LocalTime to1 = open1.getTo();
		LocalTime to2 = open2.getTo();
		return from1.getHour() == from2.getHour() && from1.getMinute() == from2.getMinute() 
				&& to1.getHour() == to2.getHour() && to1.getMinute() == to2.getMinute();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		OpeningHours other = (OpeningHours) obj;
		if (dayOfWeek != other.dayOfWeek)
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

}
