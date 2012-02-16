package com.bappli.saf.datalink.sql;

public abstract class SqlValue
{

	//---------------------------------------------------------------------------------------- escape
	public static String escape(Object value)
	{
		String stringValue;
		if (value == null) {
			stringValue = "NULL";
		} else {
			// TODO multiple elements value
			stringValue = "\"" + value.toString().replace("\"", "\\\"") + "\"";
		}
		return stringValue;
	}

}
