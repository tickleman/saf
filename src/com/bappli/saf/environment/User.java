package com.bappli.saf.environment;

public class User
{

	//----------------------------------------------------------------------------------------- login
	private String login;
	public  String getLogin() { return login; }
	public  User setLogin(String login) { this.login = login; return this; }

	//-------------------------------------------------------------------------------------- password
	private String password;
	public  String getPassword() { return password; }
	public  User setPassword(String password) { this.password = password; return this; }

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

}
