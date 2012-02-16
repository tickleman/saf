package com.bappli.saf.datalink.sql;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.bappli.saf.datalink.DataLink;

public abstract class SqlLink implements DataLink
{

	//---------------------------------------------------------------------------- objectsIdentifiers
	/**
	 * Keep here each identifier, in database, of each read / written object
	 */
	private Map<Object, Integer> objectsIdentifiers = new HashMap<Object, Integer>();
	protected Integer getObjectIdentifier(Object object) { return objectsIdentifiers.get(object); }
	protected SqlLink setObjectIdentifier(Object object, Integer id) {
		objectsIdentifiers.put(object, id); return this;
	}

	//------------------------------------------------------------------------------------ connection
	private Connection connection;
	public Connection getConnection() { return connection; }
	public SqlLink setConnection(Connection connection) { this.connection = connection; return this; }

}
