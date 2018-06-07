package de.unima.webdataintegration.location.fusion.conflictresolution;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.unima.webdataintegration.location.model.Location;
import de.unima.webdataintegration.location.model.OpeningHours;

public class OpeningHoursUnionResolution extends ConflictResolutionFunction<Set<OpeningHours>, Location, Attribute>{

	@Override
	public FusedValue<Set<OpeningHours>, Location, Attribute> resolveConflict(
			Collection<FusibleValue<Set<OpeningHours>, Location, Attribute>> values) {
		Set<OpeningHours> fused = new HashSet<>();
		for(DayOfWeek dayofweek : DayOfWeek.values()) {
			//Collect opening hours for current day of week across all locations
			List<OpeningHours> sameWeekdayHours = new ArrayList<>();
			for (FusibleValue<Set<OpeningHours>, Location, Attribute> value : values) {
				OpeningHours hours = findDayOfWeekOpeningHours(value.getValue(), dayofweek);
				if(Objects.nonNull(hours)) {
					sameWeekdayHours.add(hours);
				}
			}
			//Create intersection of gathered opening hours
			try {
			OpeningHours fusedOpeningHours = createFusedOpeningHours(sameWeekdayHours, dayofweek);
			if(Objects.nonNull(fusedOpeningHours) ) {
				fused.add(fusedOpeningHours);
			}
			}catch(Exception e) {
				System.out.println();
			}
		}
		return new FusedValue<>(fused);
	}
	
	public OpeningHours createFusedOpeningHours(List<OpeningHours> allHours, DayOfWeek dayofweek) {
		OpeningHours fused = new OpeningHours();
		fused.setDayOfWeek(dayofweek);
		Optional<LocalTime> fusedFrom = allHours.stream()
				  .filter(x -> {return Objects.nonNull(x.getFrom());})
				  .map(x -> {return x.getFrom();})
				  .min((time1, time2) -> {return (time1.isAfter(time2)) ? 1 : -1;});
		Optional<LocalTime> fusedTo = allHours.stream()
				  .filter(x -> {return Objects.nonNull(x.getTo());})
				  .map(x -> {return x.getTo();})
				  .max((time1, time2) -> {return (time1.isAfter(time2)) ? 1 : -1;});
		if(fusedFrom.isPresent()) fused.setFrom(fusedFrom.get());
		if(fusedTo.isPresent()) fused.setTo(fusedTo.get());
		return fused;
	}
	
	public OpeningHours findDayOfWeekOpeningHours(Set<OpeningHours> openingHours, DayOfWeek dayofweek) {
		for(OpeningHours day : openingHours) {
			if(day.getDayOfWeek() == dayofweek) {
				return day;
			}
		}
		return null;
	}

}
