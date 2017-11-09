package de.unima.webdataintegration.location.plugin.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.unima.webdataintegration.location.plugin.annotations.AttributeValue;

public abstract class StandardAbstractRecord<SchemaElementType extends Matchable> extends AbstractRecord<SchemaElementType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1968798558199003495L;

	private Class<SchemaElementType> clazz;
	private Collection<String> attributes;
	
	public StandardAbstractRecord(String identifier, String provenance, Class<SchemaElementType> clazz) {
		super(identifier, provenance);
		this.clazz = clazz;
		this.attributes = new ArrayList<>();
		initialiseExpectedAttributes();
	}
	
	private void initialiseExpectedAttributes() {
		for(Field field : clazz.getDeclaredFields()) {
			if(field.isAnnotationPresent(AttributeValue.class)) {
				Annotation annotation = field.getAnnotation(AttributeValue.class);
				AttributeValue attribute = (AttributeValue) annotation;
				String value = (Objects.nonNull(attribute.name()) && !attribute.name().isEmpty())
						? attribute.name() : field.getName();
				attributes.add(value);
			}
		}
	}
	
	@Override
	public boolean hasValue(SchemaElementType attribute) {
		try {
			//String value = attribute.getIdentifier();
			Field field = clazz.getDeclaredField("id");
			field.setAccessible(true);
			Object object = field.get(attribute);
			return Objects.nonNull(object);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
