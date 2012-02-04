package com.bappli.saf.environment;

//##################################################################################### Environment
public class Environment
{

	private static Environment current;
	public static Environment getCurrent() { return current; }
	public static Environment setCurrent(Environment current) { Environment.current = current; return current; } 

	//----------------------------------------------------------------------------------- Environment
	public Environment()
	{
		if (current == null) {
			current = this;
		}
	}

}
