package com.bappli.saf.datalink.mappers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			} catch (Exception e) {
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

	//---------------------------------------------------------------------------------------- getMap
	public static Map<? extends Object, ? extends Object> getMap(
		Map<? extends Object, ? extends Object> map,
		Class<? extends Object> keyClass,
		Class<? extends Object> elementClass,
		Object parent
	) {
		if (map == null) {
			Map<Object, Object> resultMap = new HashMap<Object, Object>();
			Object[] columns = { keyClass, elementClass };
			try {
				List<Object[]> result = ConnectedEnvironment.getCurrent().getDataLink().select(
					new ClassJoin(keyClass, elementClass), columns, parent);
				for (Object[] row : result) {
					resultMap.put(row[0], row[1]);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
			map = resultMap;
		}
		return map;
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
