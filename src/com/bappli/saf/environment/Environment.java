package com.bappli.saf.environment;

public class Environment
{

	private static Environment current;

	//----------------------------------------------------------------------------------- Environment
	public Environment()
	{
		if (current == null) {
			current = this;
		}
	}

	//------------------------------------------------------------------------------------ getCurrent
	public static Environment getCurrent()
	{
		return current;
	}

	//------------------------------------------------------------------------------------ setCurrent
	public static Environment setCurrent(Environment current){
		Environment.current = current; return current;
	} 

}
