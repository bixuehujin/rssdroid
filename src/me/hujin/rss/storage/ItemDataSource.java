package me.hujin.rss.storage;

import java.util.ArrayList;
import java.util.List;

import me.hujin.rss.parser.RSSFeed;
import me.hujin.rss.parser.RSSItem;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ItemDataSource {

	private SQLiteDatabase db;
	private RssRoidSqliteHelper dbHelper;
	
	
	public ItemDataSource(Context context) {
		dbHelper = new RssRoidSqliteHelper(context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Item save(RSSItem rssItem) {
		
		Item item = new Item();
		return item;
	}
	
	
	public List<Item> saveAll(RSSFeed feed) {
		
		List<Item> items= new ArrayList<Item>();
		
		return items;
	}
}
