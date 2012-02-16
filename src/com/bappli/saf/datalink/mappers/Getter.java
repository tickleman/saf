package com.bappli.saf.datalink.mappers;

import java.util.Collection;

import com.bappli.saf.environment.ConnectedEnvironment;
import com.bappli.saf.environment.Contained;

public class Getter
{

	//------------------------------------------------------------------------------------- getGetter
	public static String getGetter(String fieldName)
	{
		StringBuffer getter = new StringBuffer("get");
		String[] split = fieldName.split("\\_");
		for (int i = 0; i < split.length; i++) {
			if (split[i].length() > 0) {
				getter.append(split[i].substring(0, 1).toUpperCase()).append(split[i].substring(1));
			}
		}
		return getter.toString();
	}

	//--------------------------------------------------------------------------------- getCollection
	@SuppressWarnings("unchecked")
	public static Collection<? extends Contained> getCollection(
		Collection<? extends Contained> collection,
		Class<? extends Contained> elementClass,
		Object parent
	) {
		if (collection == null) {
			Contained element = null;
			try {
				element = elementClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
			element.setParent(parent);
			try {
				collection = (Collection<? extends Contained>)
					ConnectedEnvironment.getCurrent().getDataLink().search(element);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return collection;
	}

	//------------------------------------------------------------------------------------- getObject
	public static Object getObject(Object object, Class<? extends Object> objectClass)
	{
		if (object instanceof Integer) {
			try {
				object = ConnectedEnvironment.getCurrent().getDataLink().read(object, objectClass);
			} catch (Exception exception) {
				object = null;
			}
		}
		return object;
	}

}
