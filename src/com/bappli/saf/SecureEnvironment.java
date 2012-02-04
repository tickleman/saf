package com.bappli.saf;

import com.bappli.saf.datalink.DataLink;
import com.bappli.saf.datalink.Getter;

//############################################################################### SecureEnvironment
public class SecureEnvironment extends ConnectedEnvironment
{

	//--------------------------------------------------------------------------------------- current
	public static SecureEnvironment getCurrent() { return (SecureEnvironment)Environment.getCurrent(); }

	//------------------------------------------------------------------------------------------ user
	private User user;
	public User getUser() { return (User)Getter.getObject(user, User.class); }
	public Environment setUser(User user) { this.user = user; return this; }

	//----------------------------------------------------------------------------- SecureEnvironment
	public SecureEnvironment(DataLink dataLink, User user)
	{
		super(dataLink);
		this.user = user;
	}

}
