package com.bappli.saf.datalink.sql;

import java.sql.SQLException;
import java.util.Map;

import com.bappli.saf.annotations.CollectionName;
import com.bappli.saf.datalink.DataField;

public abstract class SqlTable
{

	//------------------------------------------------------------------------------------- getFields
	public abstract Map<String, ? extends DataField> getFields(
		SqlLink link, Class<? extends Object> objectClass
	) throws SQLException;

	//------------------------------------------------------------------------------ classToTableName
	public static String classToTableName(Class<? extends Object> objectClass)
	{
		if (objectClass.getAnnotation(CollectionName.class) != null) {
			return objectClass.getAnnotation(CollectionName.class).value().toLowerCase();
		} else {
			String className = objectClass.getSimpleName();
			if (className.substring(className.length() - 1).equals("y")) {
				return className.substring(0, -1).toLowerCase() + "ies";
			} else {
				return className.toLowerCase() + "s";
			}
		}
	}

}
