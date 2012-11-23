package me.hujin.rss.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RssRoidSqliteHelper extends SQLiteOpenHelper{

	public static final String TB_FEED = "feed";
	
	public static final String TB_ITEM = "item";
	
	public static final String DB_FILE = "rssroid.db";
	
	public static final int DB_VERSION = 1;
	
	
	public RssRoidSqliteHelper(Context context) {
		super(context, DB_FILE, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql_feed = "create table "
				+ TB_FEED + "("
				+ "fid integer primary key autoincrement, "
				+ "title text not null, "
				+ "description text not null, "
				+ "last_build_date integer,"
				+ "last_item_date integer, "
				+ "link text not null);";
		
		db.execSQL(sql_feed);
		
		String sql_item = "create table "
				+ TB_ITEM + "("
				+ "id integer primary key autoincrement, "
				+ "fid integer, "
				+ "title text not null, "
				+ "description text not null, "
				+ "content text not null, "
				+ "author text not null, "
				+ "pub_date integer, "
				+ "link text not null);";
		db.execSQL(sql_item);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
