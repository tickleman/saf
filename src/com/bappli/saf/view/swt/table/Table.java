package com.bappli.saf.view.swt.table;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.bappli.saf.datalink.DataLink;
import com.bappli.saf.datalink.mappers.ClassJoin;

public class Table
{

	Class<? extends Object> objectClass;
	String[] columns;
	List<Object[]> data;

	//------------------------------------------------------------------------------------------ List
	public Table(Class<? extends Object> objectClass, String[] columns)
	{
		this.objectClass = objectClass;
		this.columns = columns;
	}

	//--------------------------------------------------------------------------------------- display
	public Table display(Composite parent)
	{
		org.eclipse.swt.widgets.Table table = new org.eclipse.swt.widgets.Table(parent, SWT.NONE);
		for (int i = 0; i < columns.length; i++) {
			new TableColumn(table, SWT.NONE).setText(columns[i]);
		}
		for (Object[] row : data) {
			String[] strings = new String[row.length];
			for (int i = 0; i < row.length; i++) strings[i] = row[i].toString();
			new TableItem(table, SWT.NONE).setText(strings);
		}
		for (int i = 0; i < columns.length; i++) {
			table.getColumn(i).pack();
			table.getColumn(i).setMoveable(true);
		}
		table.setHeaderVisible(true);
		return this;
	}

	//-------------------------------------------------------------------------------------- linkData
	public Table linkData(DataLink dataLink) throws Exception
	{
		data = dataLink.select(new ClassJoin(objectClass), columns);
		return this;
	}

}
