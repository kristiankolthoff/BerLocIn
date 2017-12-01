package de.unima.webdataintegration.location.identityresolution.comparators;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import de.unima.webdataintegration.location.model.Location;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class LocationStreetAddressMetaComparator implements Comparator<Location, Attribute>{

	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity nameSimilarity;
	private TokenizingJaccardSimilarity nameTokenSimilarity;
	private LevenshteinSimilarity numberSimilarity;
	private float beta;
	private static final Pattern PATTERN = Pattern.compile("^([a-zäöüß\\s\\d.,-]+?)\\s*([\\d\\s]+(?:\\"
			+ "s?[-|+/]\\s?\\d+)?\\s*[a-z]?)?\\s*(\\d{5})\\s*(.+)?$");
	
	public LocationStreetAddressMetaComparator(float beta) {
		this.nameSimilarity = new LevenshteinSimilarity();
		this.nameTokenSimilarity = new TokenizingJaccardSimilarity();
		this.numberSimilarity = new LevenshteinSimilarity();
		this.beta = beta;
	}

	@Override
	public double compare(Location record1, Location record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		if(!record1.hasValue(Location.STREET_ADDRESS) || !record2.hasValue(Location.STREET_ADDRESS)) {
			return -1;
		}
		Pair<String, String> streetNumber1 = preprocss(record1);
		Pair<String, String> streetNumber2 = preprocss(record2);
		boolean nameValid = !Objects.isNull(streetNumber1.getFirst()) && !Objects.isNull(streetNumber2.getFirst());
		boolean numberValid = !Objects.isNull(streetNumber1.getSecond()) && !Objects.isNull(streetNumber2.getSecond());
		double similarityName = 0d, similarityNumber = 0d;
		if(nameValid) {
			double similarityNamePlain = nameSimilarity.calculate(streetNumber1.getFirst(), streetNumber2.getFirst());
			double similarityTokenName = nameTokenSimilarity.calculate(streetNumber1.getFirst(), streetNumber2.getFirst());
			similarityName = Math.max(similarityNamePlain, similarityTokenName);
		}
		if(numberValid) {
			similarityNumber = numberSimilarity.calculate(streetNumber1.getSecond(), streetNumber2.getSecond());
		}
		if(nameValid && numberValid) {
			return ((1 + Math.pow(beta, 2)) * similarityName * similarityNumber) / 
					((Math.pow(beta, 2) *similarityName) + similarityNumber);
		} else if(nameValid && !numberValid) {
			return similarityName;
		} else if(!nameValid && numberValid) {
			return similarityNumber;
		} else {
			return -1;
		}
	}

	private Pair<String, String> preprocss(Location location) {
		//Normalize streetaddress by removing duplicate information
		String streetAddress = location.getStreetAddress();
		String postalCode = "12345";
		if(location.hasValue(Location.POSTAL_CODE)) {
			postalCode = location.getPostalCode();
		}
		streetAddress = streetAddress
				.toLowerCase()
				.trim()
				.replaceAll("berlin", "")
				.replaceAll("germany", "")
				.replaceAll("deutschland", "")
				.replaceAll(postalCode, "")
				.replaceAll(",", "")
				.replaceAll("strasse", "str.")
				.replaceAll("straße", "str.")
				.replaceAll("str\\.", "str")
				.replaceAll("platz", "pl.")
				.replaceAll("pl\\.", "pl");
		List<String> nameTokens = getTokens(location.getName());
		for(String token : nameTokens) {
			token = token.replaceAll("\\+", "")
						 .replaceAll("\\?", "")
						 .replaceAll("\\*", "")
						 .replaceAll("\\\\", "");
			try {
				
			streetAddress = streetAddress.replaceAll(token, "");
			} catch(PatternSyntaxException e) {
				e.printStackTrace();
			}
		}
		//Join back the postalcode for the pattern to work
		streetAddress += " " + postalCode;
		Matcher matcher = PATTERN.matcher(streetAddress);
		String streetName = null, streetNumber = null;
		if(matcher.matches()) {
			streetName = matcher.group(1);
			streetNumber = matcher.group(2);
		}
		return new Pair<String, String>(streetName, streetNumber);
	}
	
	private List<String> getTokens(String sentence) {
		PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<CoreLabel>(new StringReader(sentence),
				new CoreLabelTokenFactory(), "");
		List<CoreLabel> tokens = tokenizer.tokenize();
		List<String> sTokens = new ArrayList<>();
		for(CoreLabel token : tokens) {
			sTokens.add(token.originalText());
		}
		return sTokens;
	}
	
}
