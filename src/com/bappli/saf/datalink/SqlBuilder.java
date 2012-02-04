package com.bappli.saf.datalink;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

//###################################################################################### SqlBuilder
public class SqlBuilder
{

	//----------------------------------------------------------------------------------- buildInsert
	public static String buildInsert(String tableName, Map<String, Object> write)
	{
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
			.append("INSERT INTO `")
			.append(tableName)
			.append("` (")
			.append(buildFields(write.keySet()))
			.append(") VALUES (")
			.append(buildValues(write.values()))
			.append(")");
		return sqlInsert.toString();
	}

	//----------------------------------------------------------------------------------- buildFields
	public static StringBuffer buildFields(Collection<String> fieldNames)
	{
		StringBuffer sqlFields = new StringBuffer();
		int i = fieldNames.size();
		for (String key : fieldNames) {
			sqlFields.append("`").append(key).append("`");
			if (--i > 0) {
				sqlFields.append(", ");
			}
		}
		return sqlFields;
	}

	//----------------------------------------------------------------------------------- buildUpdate
	public static String buildUpdate(String tableName, Map<String, Object> write, Integer id)
	{
		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate.append("UPDATE `").append(tableName).append("` SET ");
		int i = write.size();
		for (String key : write.keySet()) {
			String value = SqlValue.escape(write.get(key));
			sqlUpdate.append("`").append(key).append("` = ").append(value);
			if (--i > 0) {
				sqlUpdate.append(", ");
			}
		}
		sqlUpdate.append(" WHERE ROWID = ").append(id.toString());
		return sqlUpdate.toString();
	}

	//----------------------------------------------------------------------------------- buildValues
	public static StringBuffer buildValues(Collection<Object> values)
	{
		StringBuffer sqlValues = new StringBuffer();
		int i = values.size();
		for (Object value : values) {
			sqlValues.append(SqlValue.escape(value));
			if (--i > 0) {
				sqlValues.append(", ");
			}
		}
		sqlValues.append(")");
		return sqlValues;
	}

	//------------------------------------------------------------------------------------ buildWhere
	public static StringBuffer buildWhere(Object object)
	{
		StringBuffer sqlWhere = new StringBuffer();
		boolean first = true;
		for (Field field : ClassFields.accessFields(object.getClass())) {
			Object value = null;
			try {
				value = field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value != null) {
				if (first) {
					first = false;
				} else {
					sqlWhere.append(" AND ");
				}
				sqlWhere.append("`").append(field.getName()).append("` = ").append(SqlValue.escape(value));
			}
		}
		ClassFields.accessFieldsDone(object.getClass());
		return sqlWhere;
	}

}
