package com.bappli.saf.datalink.mappers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper
{

	//------------------------------------------------------------------------------------------- map
	public static Object map(ResultSet resultSet, Class<? extends Object> objectClass)
		throws IllegalAccessException, InstantiationException, SQLException
	{
		Object object = objectClass.newInstance();
		for (Field field : ClassFields.accessFields(objectClass)) {
			String fieldName = field.getName();
			int columnIndex = -1;
			try {
				columnIndex = resultSet.findColumn(fieldName);
			} catch (SQLException column_exception) {
				try {
					columnIndex = resultSet.findColumn("id_" + fieldName);
				} catch (SQLException id_column_exception) {
					columnIndex = -1;
				}
			}
			if (columnIndex >= 0) {
				Class<? extends Object> fieldType = field.getType();
				if (fieldType.equals(Double.class)) {
					field.set(object, resultSet.getDouble(columnIndex));
				} else if (fieldType.equals(String.class)) {
					field.set(object, resultSet.getString(columnIndex));
				} else {
					field.set(object, resultSet.getInt(columnIndex));
				}
			}
		}
		return object;
	}

}
