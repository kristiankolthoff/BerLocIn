package de.unima.webdataintegration.location.fusion.rules;

import java.time.DayOfWeek;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.OpeningHours;

public class OpeningHoursEvaluationRule extends EvaluationRule<Location, Attribute>{

	@Override
	public boolean isEqual(Location record1, Location record2, Attribute schemaElement) {
		Set<OpeningHours> openingHours1 = record1.getOpeningHours();
		Set<OpeningHours> openingHours2 = record2.getOpeningHours();
		boolean equals = searchForWeekdays(openingHours1, openingHours2);
		if(!equals) {
			return false;
		}
		return searchForWeekdays(openingHours2, openingHours1);
	}
	
	public boolean searchForWeekdays(Set<OpeningHours> openingHours1, Set<OpeningHours> openingHours2) {
		for(OpeningHours dayHours1 : openingHours1) {
			DayOfWeek dayOfWeek1 = dayHours1.getDayOfWeek();
			boolean foundDay = false;
			for(OpeningHours dayHours2 : openingHours2) {
				DayOfWeek dayOfWeek2 = dayHours2.getDayOfWeek();
				if(dayOfWeek1 == dayOfWeek2) {
					foundDay = true;
					if(!OpeningHours.equalFromAndTo(dayHours1, dayHours2)) {
						return false;
					}
					break;
				}
			}
			if(!foundDay) return false;
		}
		return true;
	}

	@Override
	public boolean isEqual(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
