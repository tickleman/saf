package com.bappli.saf.datalink;

//######################################################################################## SqlTable
public abstract class SqlTable
{

	//------------------------------------------------------------------------------ classToTableName
	public static String classToTableName(Class<? extends Object> objectClass)
	{
		String className = objectClass.getSimpleName();
		if (className.substring(className.length() - 1).equals("y")) {
			return className.substring(0, -1).toLowerCase() + "ies";
		} else {
			return className.toLowerCase() + "s";
		}
	}

}
