package com.bappli.saf.datalink.sqlite;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.bappli.saf.datalink.ClassFields;
import com.bappli.saf.datalink.ResultSetMapper;
import com.bappli.saf.datalink.SqlBuilder;
import com.bappli.saf.datalink.SqlLink;

//###################################################################################### SQLiteLink
public class SQLiteLink extends SqlLink
{

	//------------------------------------------------------------------------------------ SQLiteLink
	public SQLiteLink(String databasePath) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.sqlite.JDBC");
		setConnection(DriverManager.getConnection("jdbc:sqlite:" + databasePath));
	}

	//----------------------------------------------------------------------------------------- query
	@Override
	public Integer query(String query) throws SQLException
	{
		Statement statement = getConnection().createStatement();
		statement.execute(query);
		ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ROWID()");
		Integer id;
		try {
			id = resultSet.getInt(0);
		} catch (SQLException e) {
			id = null;
		}
		resultSet.close();
		return id;
	}

	//------------------------------------------------------------------------------------------ read
	@Override
	public Object read(Object identifier, Class<? extends Object> objectClass)
		throws IllegalAccessException, InstantiationException, SQLException
	{
		Integer id = (Integer)identifier;
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(
			"SELECT * FROM `" + SQLiteTable.classToTableName(objectClass) + "` WHERE ROWID = " + id
		);
		resultSet.next();
		Object object = ResultSetMapper.map(resultSet, objectClass);
		resultSet.close();
		setObjectIdentifier(object, id);
		return object;
	}

	//--------------------------------------------------------------------------------------- readAll
	@Override
	public Collection<Object> readAll(Class<? extends Object> objectClass)
		throws IllegalAccessException, InstantiationException, SQLException
	{
		Collection<Object> readResult = new ArrayList<Object>();
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(
			"SELECT ROWID, * FROM `" + SQLiteTable.classToTableName(objectClass) + "`"
		);
		while (resultSet.next()) {
			Object object = ResultSetMapper.map(resultSet, objectClass);
			setObjectIdentifier(object, resultSet.getInt("ROWID"));
			readResult.add(object);
		}
		resultSet.close();
		return readResult;
	}

	//---------------------------------------------------------------------------------------- search
	@Override
	public Collection<Object> search(Object what)
		throws IllegalAccessException, InstantiationException, SQLException
	{
		Class<? extends Object> objectClass = what.getClass();
		Collection<Object> searchResult = new ArrayList<Object>();
		StringBuffer where = SqlBuilder.buildWhere(what);
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(
			"SELECT ROWID, * FROM `" + SQLiteTable.classToTableName(objectClass) + "` WHERE " + where
		);
		while (resultSet.next()) {
			Object object = ResultSetMapper.map(resultSet, objectClass);
			setObjectIdentifier(object, resultSet.getInt("ROWID"));
			searchResult.add(object);
		}
		resultSet.close();
		return searchResult;
	}

	//----------------------------------------------------------------------------------------- write
	@Override
	public void write(Object object)
		throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String tableName = SQLiteTable.classToTableName(object.getClass());
		Set<String> tableFieldsNames = SQLiteTable.getFields(this, object.getClass()).keySet();
		Map<String, Object> write = new HashMap<String, Object>();
		for (Field classField : ClassFields.accessFields(object.getClass())) {
			if (tableFieldsNames.contains(classField.getName())) {
				write.put(classField.getName(), classField.get(object));
			}
		}
		ClassFields.accessFieldsDone(object.getClass());
		Integer id = getObjectIdentifier(object);
		if (id == null) {
			setObjectIdentifier(object, query(SqlBuilder.buildInsert(tableName, write)));
		} else {
			query(SqlBuilder.buildUpdate(tableName, write, id));
		}
	}

}
