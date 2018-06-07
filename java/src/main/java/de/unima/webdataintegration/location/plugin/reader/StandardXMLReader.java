package de.unima.webdataintegration.location.plugin.reader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.Fusible;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import de.unima.webdataintegration.location.plugin.annotations.AttributeValue;

public class StandardXMLReader<RecordType extends Matchable & Fusible<SchemaElementType>, SchemaElementType extends Matchable> 
		extends XMLMatchableReader<RecordType, SchemaElementType> implements 
		FusibleFactory<RecordType, SchemaElementType>{

	private Class<RecordType> clazzRecord;
	private Class<SchemaElementType> clazzSchema;
	private Collection<String> attributes;
	
	public StandardXMLReader(Class<RecordType> clazzRecord, Class<SchemaElementType> clazzSchema) {
		this.clazzRecord = clazzRecord;
		this.clazzSchema = clazzSchema;
		this.attributes = new ArrayList<>();
		this.initialiseExpectedAttributes();
	}
	
	private void initialiseExpectedAttributes() {
		for(Field field : clazzRecord.getDeclaredFields()) {
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
	protected void initialiseDataset(DataSet<RecordType, SchemaElementType> dataset) {
		super.initialiseDataset(dataset);
		for(String attribute : attributes) {
			try {
				dataset.addAttribute(clazzSchema.getConstructor(String.class).newInstance(attribute));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public RecordType createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		try {
			RecordType instance = clazzRecord.getConstructor(String.class, String.class)
											 .newInstance(id, provenanceInfo);
			for(String attribute : attributes) {
				Field field = clazzRecord.getDeclaredField(attribute);
				field.setAccessible(true);
				String value = getValueFromChildElement(node, attribute);
				if(field.getType() == String.class) {
					field.set(instance, value);
				} else if(field.getType() == double.class) {
					double doubleValue = Objects.nonNull(value) ? Double.parseDouble(value) : 0d;
					field.setDouble(instance, doubleValue);
				} else if(field.getType() == float.class) {
					float floatValue = Objects.nonNull(value) ? Float.parseFloat(value) : 0f;
					field.setFloat(instance, floatValue);
				} else if(field.getType() == boolean.class) {
					boolean booleanValue = Objects.nonNull(value) ? Boolean.parseBoolean(value) : false;
					field.setBoolean(instance, booleanValue);
				} else if(field.getType() == int.class) {
					int integerValue = Objects.nonNull(value) ? Integer.parseInt(value) : 0;
					field.setInt(instance, integerValue);
				} else if(field.getType() == long.class) {
					long longValue = Objects.nonNull(value) ? Long.parseLong(value) : 0L;
					field.setLong(instance, longValue);
				} else if(field.getType() == short.class) {
					short shortValue = Objects.nonNull(value) ? Short.parseShort(value) : 0;
					field.setShort(instance, shortValue);
				}
			}
			return instance;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public RecordType createInstanceForFusion(RecordGroup<RecordType, SchemaElementType> cluster) {
		StringBuilder builder = new StringBuilder();
		for(RecordType record : cluster.getRecords()) {
			builder.append(record.getIdentifier());
			builder.append("_");
		}
		try {
			return clazzRecord.getConstructor(String.class, String.class).newInstance(builder.toString(), "fused");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
