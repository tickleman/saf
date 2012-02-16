package com.bappli.saf.datalink;

public interface DataField
{

	public boolean canBeNull();

	public Object getDefault();

	public String getName();

	public Class<? extends Object> getType();

}
