package me.hujin.rss.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataSourceBase {

	protected SQLiteDatabase db;
	static protected RssRoidSqliteHelper dbHelper;
	
	
	public DataSourceBase(Context context) {
		dbHelper = new RssRoidSqliteHelper(context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
}
