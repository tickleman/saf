package com.bappli.saf.datalink.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bappli.saf.datalink.DataLink;
import com.bappli.saf.datalink.mappers.ClassJoin;
import com.bappli.saf.datalink.sqlite.SQLiteTable;

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

	//-------------------------------------------------------------------------------------- newTable
	public SQLiteTable newTable()
	{
		return new SQLiteTable();
	}

	//----------------------------------------------------------------------------------------- query
	public abstract Integer query(String query) throws SQLException;

	//---------------------------------------------------------------------------------------- select
	@Override
	public List<Object[]> select(ClassJoin classJoin, Object[] columns)
		throws ClassNotFoundException, NoSuchMethodException, SecurityException, SQLException
	{
		String query = SqlBuilder.buildSelect(classJoin, columns, this);
		return select(query, columns.length);
	}

	//---------------------------------------------------------------------------------------- select
	@Override
	public List<Object[]> select(ClassJoin classJoin, Object[] columns, Object filterObject)
		throws NoSuchMethodException, SecurityException, SQLException
	{
		Map<String, Object> filterMap = new HashMap<String, Object>();
		filterMap.put(
			"id_" + filterObject.getClass().getSimpleName().toLowerCase(),
			getObjectIdentifier(filterObject)
		);
		String query = SqlBuilder.buildSelect(classJoin, columns, this)
			+ SqlBuilder.buildWhere(filterMap);
		return select(query, columns.length);
	}

	//---------------------------------------------------------------------------------------- select
	private List<Object[]> select(String query, int listLength)
		throws SQLException
	{
		List<Object[]> list = new ArrayList<Object[]>();
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		int columnCount = resultSet.getMetaData().getColumnCount();
		String[] columnNames = new String[columnCount];
		Map<Class<? extends Object>, Integer> classesIdx
			= new HashMap<Class<? extends Object>, Integer>();
		int[] itoj = new int[columnCount];
		@SuppressWarnings("unchecked")
		Class<? extends Object>[] classes = new Class[listLength];
		boolean[] createObject = new boolean[listLength];
		int j = 0;
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = resultSet.getMetaData().getColumnName(i + 1);
			if (!columnNames[i].contains(":")) {
				itoj[i] = j++;
			} else {
				String[] split = columnNames[i].split("\\:");
				columnNames[i] = split[1];
				Class<? extends Object> objectClass = null;
				try {
					objectClass = Class.forName(split[0]);
				} catch (ClassNotFoundException e) {
					System.out.println(e.getMessage());
					e.printStackTrace(System.out);
				}
				Integer hisj = classesIdx.get(objectClass);
				if (hisj == null) {
					hisj = j;
					classesIdx.put(objectClass, j);
					createObject[j] = true;
					itoj[i] = j++;
				} else {
					itoj[i] = hisj;
				}
				classes[hisj] = objectClass;
			}
			if ((columnNames[i].length() > 3) && columnNames[i].substring(0, 3).equals("id_")) {
				columnNames[i] = columnNames[i].substring(3);
			}
		}
		while (resultSet.next()) {
			Object[] row = new Object[listLength];
			for (int i = 0; i < columnCount; i++) {
				j = itoj[i];
				if (!(classes[j] instanceof Class)) {
					row[j] = resultSet.getObject(i + 1);
				} else {
					if (!(row[j] instanceof Object)) {
						// TODO try to get the object from an object map (avoid several instances of the same)
						try {
							row[j] = classes[j].newInstance();
						} catch (Exception e) {
							System.out.println(e.getMessage());
							e.printStackTrace(System.out);
						}
					}
					if (columnNames[i].equals("id")) {
						setObjectIdentifier(row[j], resultSet.getInt(i + 1));
					} else {
						try {
							Field classField = classes[j].getDeclaredField(columnNames[i]);
							boolean isAccessible = classField.isAccessible();
							if (!isAccessible) classField.setAccessible(true);
							classField.set(row[j], resultSet.getObject(i + 1));
							if (isAccessible) classField.setAccessible(false);
						} catch (Exception e) {
							System.out.println(e.getMessage());
							e.printStackTrace(System.out);
						}
					}
				}
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
