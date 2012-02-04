package com.bappli.saf.datalink;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

//################################################################################# ResultSetMapper
public class ResultSetMapper
{

	//------------------------------------------------------------------------------------------- map
	public static Object map(ResultSet resultSet, Class<? extends Object> objectClass)
		throws IllegalAccessException, InstantiationException, SQLException
	{
		Object object = objectClass.newInstance();
		for (Field field : ClassFields.accessFields(objectClass)) {
			String fieldName = field.getName();
			int columnIndex = resultSet.findColumn(fieldName);
			Class<? extends Object> fieldType = field.getType();
			if      (fieldType.equals(Double.class))  field.set(object, resultSet.getDouble(columnIndex));
			else if (fieldType.equals(Integer.class)) field.set(object, resultSet.getInt(columnIndex));
			else if (fieldType.equals(String.class))  field.set(object, resultSet.getString(columnIndex));
			else System.err.println(
				"type not found " + fieldType.getSimpleName() + " for " + objectClass.getName() + "." + fieldName
			);
		}
		return object;
	}

}
