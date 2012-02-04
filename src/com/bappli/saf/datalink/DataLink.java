package com.bappli.saf.datalink;

import java.util.Collection;

//######################################################################################## DataLink
public interface DataLink
{

	//----------------------------------------------------------------------------------------- query
	public Integer query(String query) throws Exception;

	//------------------------------------------------------------------------------------------ read
	public Object read(Object identifier, Class<? extends Object> objectClass) throws Exception;

	//--------------------------------------------------------------------------------------- readAll
	public Collection<? extends Object> readAll(Class<? extends Object> objectClass) throws Exception;

	//---------------------------------------------------------------------------------------- search
	public Collection<? extends Object> search(Object what) throws Exception;

	//----------------------------------------------------------------------------------------- write
	public void write(Object object) throws Exception;

}
