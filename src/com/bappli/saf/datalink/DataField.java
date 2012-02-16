package com.bappli.saf.datalink;

public interface DataField
{

	//------------------------------------------------------------------------------------- canBeNull
	public boolean canBeNull();

	//------------------------------------------------------------------------------------ getDefault
	public Object getDefault();

	//--------------------------------------------------------------------------------------- getName
	public String getName();

	//--------------------------------------------------------------------------------------- getType
	public Class<? extends Object> getType();

}
