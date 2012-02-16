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

import com.bappli.saf.datalink.mappers.ClassFields;
import com.bappli.saf.datalink.mappers.ResultSetMapper;
import com.bappli.saf.datalink.sql.SqlBuilder;
import com.bappli.saf.datalink.sql.SqlLink;
import com.bappli.saf.environment.Contained;

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
			"SELECT * FROM `" + SQLiteTable.classToTableName(objectClass) + "` WHERE `id` = " + id
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
			"SELECT * FROM `" + SQLiteTable.classToTableName(objectClass) + "`"
		);
		while (resultSet.next()) {
			Object object = ResultSetMapper.map(resultSet, objectClass);
			setObjectIdentifier(object, resultSet.getInt("id"));
			readResult.add(object);
		}
		resultSet.close();
		return readResult;
	}

	//--------------------------------------------------------------------------------------- readAll
	@Override
	public Collection<Object> readAll(
		Class<? extends Object> objectClass,
		Class<? extends Collection<? extends Object>> collectionClass
	) throws IllegalAccessException, InstantiationException, SQLException
	{
		@SuppressWarnings("unchecked")
		Collection<Object> readResult = (Collection<Object>)collectionClass.newInstance();
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(
			"SELECT * FROM `" + SQLiteTable.classToTableName(collectionClass) + "`"
		);
		while (resultSet.next()) {
			Object object = ResultSetMapper.map(resultSet, objectClass);
			setObjectIdentifier(object, resultSet.getInt("id"));
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
		StringBuffer where = SqlBuilder.buildWhere(what, SQLiteTable.class, this);
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(
			"SELECT * FROM `" + SQLiteTable.classToTableName(objectClass) + "`" + where
		);
		while (resultSet.next()) {
			Object object = ResultSetMapper.map(resultSet, objectClass);
			setObjectIdentifier(object, resultSet.getInt("id"));
			searchResult.add(object);
		}
		resultSet.close();
		return searchResult;
	}

	//---------------------------------------------------------------------------------------- search
	@Override
	public Collection<Object> search(
		Object what, Class<? extends Collection<? extends Object>> collectionClass
	) throws IllegalAccessException, InstantiationException, SQLException
	{
		Class<? extends Object> objectClass = what.getClass();
		@SuppressWarnings("unchecked")
		Collection<Object> searchResult = (Collection<Object>)collectionClass.newInstance();
		StringBuffer where = SqlBuilder.buildWhere(what, SQLiteTable.class, this);
		Statement statement = getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(
			"SELECT * FROM `" + SQLiteTable.classToTableName(collectionClass) + "`" + where
		);
		while (resultSet.next()) {
			Object object = ResultSetMapper.map(resultSet, objectClass);
			setObjectIdentifier(object, resultSet.getInt("id"));
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
		Set<String> tableFieldsNames = new SQLiteTable().getFields(this, object.getClass()).keySet();
		Map<String, Object> write = new HashMap<String, Object>();
		for (Field classField : ClassFields.accessFields(object.getClass())) {
			if (tableFieldsNames.contains(classField.getName())) {
				write.put(classField.getName(), classField.get(object));
			} else if (tableFieldsNames.contains("id_" + classField.getName())) {
				Object value = classField.get(object);
				if (!(value instanceof Integer)) {
					value = getObjectIdentifier(value);
				}
				write.put("id_" + classField.getName(), value);
			} else if (classField.get(object) instanceof Collection) {
				@SuppressWarnings("unchecked")
				Collection<Contained> collection = (Collection<Contained>) classField.get(object);
				for (Object element : collection) {
					if (element instanceof Contained) {
						write(element);
					}
				}
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
