package de.unima.webdataintegration.location.model;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class DistrictXMLReader extends XMLMatchableReader<District, Attribute> implements
				FusibleFactory<District, Attribute>{

	
	@Override
	protected void initialiseDataset(DataSet<District, Attribute> dataset) {
		super.initialiseDataset(dataset);
		dataset.addAttribute(District.NAME);
		dataset.addAttribute(District.AREA_CODE);
	}

	@Override
	public District createModelFromElement(Node node, String provenanceInfo) {
		District district = new District("id2", provenanceInfo);
		district.setName(getValueFromChildElement(node, "name"));
		district.setAreaTotal(Double.parseDouble(getValueFromChildElement(node, "areaTotal")));
		district.setAdministrativeDistrict(getValueFromChildElement(node, "administrativeDistrict"));
		district.setAreaCode(getValueFromChildElement(node, "areaCode"));
		district.setLatitude(Double.parseDouble(getValueFromChildElement(node, "latitude")));
		district.setLongitude(Double.parseDouble(getValueFromChildElement(node, "longitude")));
		district.setIncidents(Integer.getInteger(getValueFromChildElement(node, "incidents")));
		district.setHz(Integer.getInteger(getValueFromChildElement(node, "hz")));
		return district;
	}

	@Override
	public District createInstanceForFusion(RecordGroup<District, Attribute> cluster) {
		StringBuilder builder = new StringBuilder();
		for(District district : cluster.getRecords()) {
			builder.append(district.getIdentifier());
			builder.append("_");
		}
		District district = new District(builder.toString(), "fused");
		return district;
	}

}
