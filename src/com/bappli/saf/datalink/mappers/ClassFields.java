package com.bappli.saf.datalink.mappers;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ClassFields
{

	//------------------------------------------------------------------------------ accessibleFields
	static private Stack<Map<Field, Boolean>> accessibleFieldsStack = new Stack<Map<Field, Boolean>>();

	//---------------------------------------------------------------------------------- accessFields
	public static Collection<Field> accessFields(Class<? extends Object> objectClass)
	{
		Map<Field, Boolean> accessibleFields = accessibleFieldsStack.push(
			new HashMap<Field, Boolean>()
		);
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
		Map<Field, Boolean> accessibleFields = accessibleFieldsStack.pop();
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
