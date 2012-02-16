package com.bappli.saf.datalink.sql;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.bappli.saf.datalink.mappers.ClassFields;
import com.bappli.saf.datalink.mappers.Getter;

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

	//----------------------------------------------------------------------------------- buildSelect
	public static String buildSelect(Class<? extends Object> objectClass, String[] columns)
		throws NoSuchMethodException, SecurityException
	{
		String tableName = SqlTable.classToTableName(objectClass);
		return buildSelect(objectClass, columns, tableName, 1, new StringBuffer("`" + tableName + "`"));
	}

	//----------------------------------------------------------------------------------- buildSelect
	private static String buildSelect(
		Class<? extends Object> objectClass, String[] columns, String tableName,
		int aliasCounter, StringBuffer sqlFrom
	) throws NoSuchMethodException, SecurityException
	{
		Map<Class<? extends Object>, String> tableAliases
			= new HashMap<Class<? extends Object>, String>();
		StringBuffer sqlSelect = new StringBuffer();
		Set<Field> fields = ClassFields.accessFields(objectClass);
		for (String column : columns) {
			if (!column.contains(".")) {
				sqlSelect.append(", `" + tableName + "`.`" + column + "`");
			} else {
				// TODO column.subcolumn.subcolumn2 (today it works only with column.subcolumn)
				// TODO collection columns
				String[] splitColumns = column.split("\\.");
				for (Field field : fields) if (field.getName().equals(splitColumns[0])) {
					String getter = Getter.getGetter(splitColumns[0]);
					Class<? extends Object> fieldClass = objectClass.getMethod(getter).getReturnType();
					String joinedTableName = SqlTable.classToTableName(fieldClass);
					String tableAlias = tableAliases.get(fieldClass);
					if (tableAlias == null) {
						tableAlias = "t" + aliasCounter;
						tableAliases.put(fieldClass, tableAlias);
						sqlFrom.append(" LEFT JOIN `").append(joinedTableName).append("` AS `")
							.append(tableAlias).append("` ON `").append(tableAlias).append("`.`id` = `")
							.append(tableName).append("`.`id_").append(splitColumns[0]).append("`");
					}
					sqlSelect.append(", `").append(tableAlias).append("`.`")
						.append(splitColumns[1]).append("`");
				}
			}
		}
		return "SELECT " + sqlSelect.substring(2) + " FROM " + sqlFrom.toString();
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
		sqlUpdate.append(" WHERE `id` = ").append(id.toString());
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
	public static StringBuffer buildWhere(
		Object object, Class<? extends SqlTable> sqlTableClass, SqlLink sqlLink
	) {
		Set<String> fieldNames = null;
		try {
			fieldNames = sqlTableClass.newInstance().getFields(sqlLink, object.getClass()).keySet();
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
		}
		StringBuffer sqlWhere = new StringBuffer();
		boolean first = true;
		for (Field field : ClassFields.accessFields(object.getClass())) {
			Object value = null;
			try {
				value = field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
			if (value != null) {
				String fieldName = field.getName();
				if (!fieldNames.contains(fieldName)) {
					fieldName = "id_" + fieldName;
					value = sqlLink.getObjectIdentifier(value);
				}
				if (fieldNames.contains(fieldName)) {
					if (first) {
						first = false;
					} else {
						sqlWhere.append(" AND ");
					}
					sqlWhere.append("`").append(fieldName).append("` = ").append(SqlValue.escape(value));
				} else {
					System.out.println("Exception 444122549");
				}
			}
		}
		ClassFields.accessFieldsDone(object.getClass());
		if (sqlWhere.length() > 0) {
			sqlWhere.insert(0, " WHERE ");
		}
		return sqlWhere;
	}

}
