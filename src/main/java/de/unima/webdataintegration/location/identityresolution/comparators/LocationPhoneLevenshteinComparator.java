package de.unima.webdataintegration.location.identityresolution.comparators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.unima.webdataintegration.location.model.Location;

public class LocationPhoneLevenshteinComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 3337073195037216870L;
	private String defaultRegion;
	private boolean useNationalNumberOnly;
	private PhoneNumberUtil phoneUtil;
	private LevenshteinSimilarity similarity;
	
	public static final String REGION_DE = "DE";
	
	public LocationPhoneLevenshteinComparator(String defaultRegion, boolean useNationalNumberOnly) {
		this.similarity = new LevenshteinSimilarity();
		this.phoneUtil = PhoneNumberUtil.getInstance();
		this.defaultRegion = defaultRegion;
		this.useNationalNumberOnly = useNationalNumberOnly;
	}
	
	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.PHONE) || !record2.hasValue(Location.PHONE)) {
			return -1;
		}
		try {
			String phone1 = record1.getPhone();
			PhoneNumber phoneNumber1 = phoneUtil.parse(phone1, defaultRegion);
			String phone2 = record2.getPhone();
			PhoneNumber phoneNumber2 = phoneUtil.parse(phone2, defaultRegion);
			if(phoneUtil.isValidNumberForRegion(phoneNumber1, defaultRegion) 
					&& phoneUtil.isValidNumberForRegion(phoneNumber2, defaultRegion)) {
				if(useNationalNumberOnly) {
					String first = String.valueOf(phoneNumber1.getNationalNumber());
					String second = String.valueOf(phoneNumber2.getNationalNumber());
					return similarity.calculate(first, second);
				} else {
					String first = phoneUtil.formatInOriginalFormat(phoneNumber1, defaultRegion);
					String second = phoneUtil.formatInOriginalFormat(phoneNumber2, defaultRegion);
					return similarity.calculate(first, second);
				}
			}
		} catch (NumberParseException e) {
			return -1;
		}
		return -1;
	}
	

}
