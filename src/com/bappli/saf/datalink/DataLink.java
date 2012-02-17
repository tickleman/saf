package com.bappli.saf.datalink;

import java.util.Collection;
import java.util.List;

import com.bappli.saf.datalink.mappers.ClassJoin;
import com.bappli.saf.datalink.sqlite.SQLiteTable;

public interface DataLink
{

	//-------------------------------------------------------------------------------------- newTable
	public SQLiteTable newTable();

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
	List<Object[]> select(ClassJoin classJoin, Object[] columns) throws Exception;

	//---------------------------------------------------------------------------------------- select
	List<Object[]> select(ClassJoin classJoin, Object[] columns, Object filterObject)
		throws Exception;

	//----------------------------------------------------------------------------------------- write
	public void write(Object object) throws Exception;

}
