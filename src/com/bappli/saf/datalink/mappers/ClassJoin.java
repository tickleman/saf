package com.bappli.saf.datalink.mappers;

import java.util.HashSet;
import java.util.Set;

public class ClassJoin
{

	private Set<Class<? extends Object>> joinedClasses = new HashSet<Class<? extends Object>>();
	private Class<? extends Object> mainClass;
	
	//------------------------------------------------------------------------------------- ClassJoin
	public ClassJoin(Class<? extends Object> mainClass)
	{
		this.mainClass = mainClass;
	}

	//------------------------------------------------------------------------------------- ClassJoin
	public ClassJoin(Class<? extends Object> mainClass, Class<? extends Object> joinedClass)
	{
		this.mainClass = mainClass;
		this.joinedClasses.add(joinedClass);
	}

	//------------------------------------------------------------------------------------------- add
	public ClassJoin add(Class<? extends Object> joinedClass)
	{
		this.joinedClasses.add(joinedClass);
		return this;
	}

	//------------------------------------------------------------------------------ getJoinedClasses
	public Set<Class<? extends Object>> getJoinedClasses()
	{
		return joinedClasses;
	}

	//---------------------------------------------------------------------------------- getMainClass
	public Class<? extends Object> getMainClass()
	{
		return mainClass;
	}

}
