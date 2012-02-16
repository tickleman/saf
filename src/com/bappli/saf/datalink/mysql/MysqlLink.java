package com.bappli.saf.datalink.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

import com.bappli.saf.datalink.sql.SqlLink;

public class MysqlLink extends SqlLink
{

	//------------------------------------------------------------------------------------- MysqlLink
	public MysqlLink(
		String host, String database, String user, String password
	) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.myslq.jdbc");
		setConnection(DriverManager.getConnection(
			"jdbc:mysql://" + host + "/" + database, user, password
		));
	}

	//----------------------------------------------------------------------------------------- query
	@Override
	public Integer query(String query)
	{
		// TODO
		return null;
	}

	//------------------------------------------------------------------------------------------ read
	@Override
	public Object read(Object identifier, Class<? extends Object> objectClass)
	{
		// TODO
		return null;
	}

	//--------------------------------------------------------------------------------------- readAll
	@Override
	public Collection<? extends Object> readAll(
		Class<? extends Object> objectClass,
		Class<? extends Collection<? extends Object>> collectionClass
	) throws Exception
	{
		// TODO
		return null;
	}

	//--------------------------------------------------------------------------------------- readAll
	@Override
	public Collection<Object> readAll(Class<? extends Object> objectClass)
	{
		// TODO
		return null;
	}

	//---------------------------------------------------------------------------------------- search
	@Override
	public Collection<Object> search(Object what)
	{
		// TODO
		return null;
	}

	//---------------------------------------------------------------------------------------- search
	@Override
	public Collection<Object> search(Object what,
			Class<? extends Collection<? extends Object>> collectionClass)
			throws Exception
	{
		// TODO
		return null;
	}

	//----------------------------------------------------------------------------------------- write
	@Override
	public void write(Object object)
	{
		// TODO
	}

}
