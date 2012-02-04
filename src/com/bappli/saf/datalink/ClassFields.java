package com.bappli.saf.datalink;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//###################################################################################### ClassField
public class ClassFields
{

	//------------------------------------------------------------------------------ accessibleFields
	static private Map<Field, Boolean> accessibleFields = new HashMap<Field, Boolean>();

	//---------------------------------------------------------------------------------- accessFields
	public static Collection<Field> accessFields(Class<? extends Object> objectClass)
	{
		accessibleFields.clear();
		while (objectClass != null) {
			for (Field field : objectClass.getDeclaredFields()) {
				Boolean isAccessible = field.isAccessible();
				accessibleFields.put(field, isAccessible);
				if (!isAccessible) {
					field.setAccessible(true);
				}
			}
			objectClass = objectClass.getSuperclass();
		}
		return accessibleFields.keySet();
	}

	//---------------------------------------------------------------------------------- accessFields
	public static void accessFieldsDone(Class<? extends Object> objectClass)
	{
		while (objectClass != null) {
			for (Field field : objectClass.getDeclaredFields()) {
				if (accessibleFields.get(field).equals(false)) {
					field.setAccessible(false);
				}
			}
			objectClass = objectClass.getSuperclass();
		}
	}

}
