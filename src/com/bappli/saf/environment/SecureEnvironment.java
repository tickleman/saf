package com.bappli.saf.environment;

import com.bappli.saf.datalink.DataLink;
import com.bappli.saf.datalink.mappers.Getter;

public class SecureEnvironment extends ConnectedEnvironment
{

	private Object user;

	//--------------------------------------------------------------------------------------- current
	public static SecureEnvironment getCurrent()
	{
		return (SecureEnvironment)Environment.getCurrent();
	}

	//--------------------------------------------------------------------------------------- getUser
	public User getUser()
	{
		return (User)Getter.getObject(user, User.class);
	}

	//--------------------------------------------------------------------------------------- setUser
	public Environment setUser(User user)
	{
		this.user = user;
		return this;
	}

	//----------------------------------------------------------------------------- SecureEnvironment
	public SecureEnvironment(DataLink dataLink, User user)
	{
		super(dataLink);
		this.user = user;
	}

}
