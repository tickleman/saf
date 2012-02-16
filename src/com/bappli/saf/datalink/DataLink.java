package com.bappli.saf.datalink;

import java.util.Collection;

public interface DataLink
{

	//----------------------------------------------------------------------------------------- query
	public Integer query(String query) throws Exception;

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

	//----------------------------------------------------------------------------------------- write
	public void write(Object object) throws Exception;


}
