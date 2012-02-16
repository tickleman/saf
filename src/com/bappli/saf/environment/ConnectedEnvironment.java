package com.bappli.saf.environment;

import java.util.Collection;

import com.bappli.saf.datalink.DataLink;

public class ConnectedEnvironment extends Environment
{

	//--------------------------------------------------------------------------------------- current
	public static ConnectedEnvironment getCurrent() {
		return (ConnectedEnvironment)Environment.getCurrent();
	}
	
	//-------------------------------------------------------------------------------------- dataLink
	private DataLink dataLink;
	public DataLink getDataLink() { return dataLink; }
	public Environment setDataLink(DataLink dataLink) { this.dataLink = dataLink; return this; }

	//-------------------------------------------------------------------------- ConnectedEnvironment
	public ConnectedEnvironment(DataLink dataLink)
	{
		super();
		this.dataLink = dataLink;
	}

	//--------------------------------------------------------------------------------------- readAll
	public Collection<? extends Object> readAll(Class<? extends Object> objectClass) throws Exception
	{
		return getDataLink().readAll(objectClass);
	}

	//---------------------------------------------------------------------------------------- search
	public Collection<? extends Object> search(Object what) throws Exception
	{
		return getDataLink().search(what);
	}

	//----------------------------------------------------------------------------------------- write
	public void write(Object object) throws Exception
	{
		getDataLink().write(object);
	}

}
