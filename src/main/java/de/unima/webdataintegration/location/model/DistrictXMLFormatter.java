package de.unima.webdataintegration.location.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class DistrictXMLFormatter extends XMLFormatter<District>{

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("districts");
	}

	@Override
	public Element createElementFromRecord(District record, Document doc) {
		Element district = doc.createElement("district");
		district.appendChild(createTextElement("name", record.getName(), doc));
		district.appendChild(createTextElement("areaTotal", String.valueOf(record.getAreaTotal()), doc));
		district.appendChild(createTextElement("administrativeDistrict", record.getAdministrativeDistrict(), doc));
		district.appendChild(createTextElement("areaCode", record.getAreaCode(), doc));
		district.appendChild(createTextElement("population", String.valueOf(record.getPopulation()), doc));
		district.appendChild(createTextElement("latitude", String.valueOf(record.getLatitude()), doc));
		district.appendChild(createTextElement("longitude", String.valueOf(record.getLongitude()), doc));
		district.appendChild(createTextElement("incidents", String.valueOf(record.getIncidents()), doc));
		district.appendChild(createTextElement("hz", String.valueOf(record.getHz()), doc));
		return district;
	}

}
