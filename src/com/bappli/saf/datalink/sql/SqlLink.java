package com.bappli.saf.datalink.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bappli.saf.datalink.DataLink;

public abstract class SqlLink implements DataLink
{

	/**
	 * Connection to a JDBC SQL driver
	 */
	private Connection connection;

	/**
	 * Keep here each identifier, in database, of each read / written object
	 */
	private Map<Object, Integer> objectsIdentifiers = new HashMap<Object, Integer>();

	//--------------------------------------------------------------------------------- getConnection
	public Connection getConnection()
	{
		return connection;
	}

	//--------------------------------------------------------------------------- getObjectIdentifier
	protected Integer getObjectIdentifier(Object object)
	{
		return objectsIdentifiers.get(object);
	}

	//----------------------------------------------------------------------------------------- query
	public abstract Integer query(String query) throws SQLException;

	//---------------------------------------------------------------------------------------- select
	@Override
	public List<String[]> select(Class<? extends Object> objectClass, String[] columns)
		throws NoSuchMethodException, SecurityException, SQLException
	{
		String query = SqlBuilder.buildSelect(objectClass, columns);
		List<String[]> list = new ArrayList<String[]>();
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		int columnCount = resultSet.getMetaData().getColumnCount();
		while (resultSet.next()) {
			String[] row = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				row[i] = resultSet.getString(i + 1); 
			}
			list.add(row);
		}
		return list;
	}

	//--------------------------------------------------------------------------------- setConnection
	public SqlLink setConnection(Connection connection)
	{
		this.connection = connection;
		return this;
	}

	//--------------------------------------------------------------------------- setObjectIdentifier
	protected SqlLink setObjectIdentifier(Object object, Integer id)
	{
		objectsIdentifiers.put(object, id);
		return this;
	}

}
