package com.bappli.saf.datalink;

import java.util.ArrayList;
import java.util.Collection;

import com.bappli.saf.environment.ConnectedEnvironment;
import com.bappli.saf.environment.Contained;

//########################################################################################## Getter
public class Getter
{

	//--------------------------------------------------------------------------------- getCollection
	@SuppressWarnings("unchecked")
	public static Collection<? extends Contained> getCollection(
		Collection<? extends Contained> collection, Class<? extends Contained> objectClass, Object parent
	) {
		if (collection == null) {
			try {
				collection = (Collection<Contained>)ConnectedEnvironment.getCurrent().getDataLink().search(
					objectClass.newInstance().setParent(parent)
				);
			} catch (Exception exception) {
				collection = new ArrayList<Contained>();
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
