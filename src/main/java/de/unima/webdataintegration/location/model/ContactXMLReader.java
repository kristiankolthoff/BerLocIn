package de.unima.webdataintegration.location.model;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class ContactXMLReader extends XMLMatchableReader<Contact, Attribute> {

	@Override
	protected void initialiseDataset(DataSet<Contact, Attribute> dataset) {
		super.initialiseDataset(dataset);
		dataset.addAttribute(Contact.EMAIL);
		dataset.addAttribute(Contact.WEBSITE);
		dataset.addAttribute(Contact.PHONE);
	}
	
	@Override
	public Contact createModelFromElement(Node node, String provenanceInfo) {
		Contact contact = new Contact();
		contact.setEmail(getValueFromChildElement(node, "email"));
		contact.setWebsite(getValueFromChildElement(node, "website"));
		contact.setPhone(getValueFromChildElement(node, "phone"));
		return (contact.getEmail() == null && contact.getWebsite() == null &&
				contact.getPhone() == null) ? null : contact;
	}

}
