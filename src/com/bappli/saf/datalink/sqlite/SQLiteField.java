package com.bappli.saf.datalink.sqlite;

import java.sql.ResultSet;

import com.bappli.saf.datalink.DataField;

public class SQLiteField implements DataField
{

	private String defaultValue;
	private String name;
	private String type;
	private boolean notNull;

	//--------------------------------------------------------------------------- parseResultSetEntry
	public static SQLiteField parseResultSetEntry(ResultSet resultSet)
	{
		SQLiteField field = new SQLiteField();
		try { field.defaultValue = resultSet.getString("dflt value"); } catch (Exception e) {}
		try { field.name = resultSet.getString("name"); } catch (Exception e) {}
		try { field.type = resultSet.getString("type"); } catch (Exception e) {}
		try { field.notNull = resultSet.getBoolean("notnull"); } catch (Exception e) {}
		return field;
	}

	//------------------------------------------------------------------------------------- canBeNull
	public boolean canBeNull()
	{
		return !notNull;
	}

	//------------------------------------------------------------------------------------ getDefault
	public Object getDefault()
	{
		if (type.contains("INT")) {
			return Integer.parseInt(defaultValue);
		}
		if (type.contains("CHAR") || type.contains("CLOB") || type.contains("TEXT")) {
			return defaultValue;
		}
		if (type.contains("REAL") || type.contains("FLOA") || type.contains("DOUB")) {
			return Double.parseDouble(defaultValue);
		}
		// TODO BLOB
		// TODO NUMERIC
		return defaultValue;
	}

	//--------------------------------------------------------------------------------------- getName
	public String getName()
	{
		return name;
	}

	//--------------------------------------------------------------------------------------- getType
	public Class<? extends Object> getType()
	{
		if (type.contains("INT")) {
			return Integer.class;
		}
		if (type.contains("CHAR") || type.contains("CLOB") || type.contains("TEXT")) {
			return String.class;
		}
		if (type.contains("REAL") || type.contains("FLOA") || type.contains("DOUB")) {
			return Double.class; 
		}
		// TODO BLOB
		// TODO NUMERIC
		return Object.class;
	}

}
