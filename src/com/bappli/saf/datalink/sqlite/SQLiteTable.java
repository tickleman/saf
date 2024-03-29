package com.bappli.saf.datalink.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.bappli.saf.datalink.sql.SqlLink;
import com.bappli.saf.datalink.sql.SqlTable;

public class SQLiteTable extends SqlTable
{

	//------------------------------------------------------------------------------------- getFields
	@Override
	public Map<String, SQLiteField> getFields(
		SqlLink link, Class<? extends Object> objectClass
	) throws SQLException
	{
		Map<String, SQLiteField> fields = new HashMap<String, SQLiteField>();
		Statement statement = link.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(
			"PRAGMA table_info(`" + SQLiteTable.classToTableName(objectClass) + "`)"
		);
		while (resultSet.next()) {
			SQLiteField field = SQLiteField.parseResultSetEntry(resultSet);
			fields.put(field.getName(), field);
		}
		resultSet.close();
		return fields;
	}

}
