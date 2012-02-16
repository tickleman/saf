package com.bappli.saf.environment;

public class User
{

	private String login;

	private String password;

	//------------------------------------------------------------------------------------------ User
	public User()
	{
	}

	//------------------------------------------------------------------------------------------ User
	public User(String login)
	{
		this.login = login;
	}

	//------------------------------------------------------------------------------------------ User
	public User(String login, String password)
	{
		this.login = login;
		this.password = password;
	}

	//-------------------------------------------------------------------------------------- getLogin
	public  String getLogin()
	{
		return login;
	}

	//----------------------------------------------------------------------------------- getPassword
	public  String getPassword()
	{
		return password;
	}

	//-------------------------------------------------------------------------------------- setLogin
	public  User setLogin(String login)
	{
		this.login = login;
		return this;
	}

	//----------------------------------------------------------------------------------- setPassword
	public  User setPassword(String password)
	{
		this.password = password;
		return this;
	}

}
