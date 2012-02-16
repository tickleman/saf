package com.bappli.saf.datalink;

import java.util.Collection;
import java.util.List;

public interface DataLink
{

	//------------------------------------------------------------------------------------------ read
	public Object read(Object identifier, Class<? extends Object> objectClass) throws Exception;

	//--------------------------------------------------------------------------------------- readAll
	public Collection<? extends Object> readAll(Class<? extends Object> objectClass) throws Exception;

	//--------------------------------------------------------------------------------------- readAll
	public Collection<? extends Object> readAll(
		Class<? extends Object> objectClass,
		Class<? extends Collection<? extends Object>> collectionClass
	) throws Exception;

	//---------------------------------------------------------------------------------------- search
	public Collection<? extends Object> search(Object what) throws Exception;

	//---------------------------------------------------------------------------------------- search
	public Collection<? extends Object> search(
		Object what, Class<? extends Collection<? extends Object>> collectionClass
	) throws Exception;

	//---------------------------------------------------------------------------------------- select
	List<String[]> select(Class<? extends Object> objectClass, String[] columns) throws Exception;

	//----------------------------------------------------------------------------------------- write
	public void write(Object object) throws Exception;

}
