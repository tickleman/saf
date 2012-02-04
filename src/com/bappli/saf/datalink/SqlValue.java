package com.bappli.saf.datalink;

//######################################################################################## SqlValue
public abstract class SqlValue
{

	//---------------------------------------------------------------------------------------- escape
	public static String escape(Object value)
	{
		String stringValue;
		if (value == null) {
			stringValue = "NULL";
		} else {
			// TODO multiple elements value, escape string
			stringValue = "\"" + value.toString() + "\"";
		}
		return stringValue;
	}

}
