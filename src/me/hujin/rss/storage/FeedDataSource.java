package me.hujin.rss.storage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FeedDataSource {

	private SQLiteDatabase db;
	private RssRoidSqliteHelper dbHelper;
	
	
	public FeedDataSource(Context context) {
		dbHelper = new RssRoidSqliteHelper(context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Feed addFeed() {
		
		Feed newFeed = new Feed();
		
		return newFeed;
	}
	
	
	public List<Feed> getAllFeeds() {
		List<Feed> feeds = new ArrayList<Feed>();
		Cursor cursor = db.query(
				RssRoidSqliteHelper.TB_FEED, 
				new String[]{"fid", "title", "description", "link"}, 
				null, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			Feed newFeed = cursorToFeed(cursor);
			feeds.add(newFeed);
			cursor.moveToNext();
		}
		
		cursor.close();
		return feeds;
	}
	
	/**
	 * helper method for getAllFeeds()
	 * @param cursor
	 * @return
	 */
	public Feed cursorToFeed(Cursor cursor) {
		Feed feed = new Feed();
		feed.setFid(cursor.getLong(0));
		feed.setTitle(cursor.getString(1));
		feed.setDescription(cursor.getString(2));
		feed.setLink(cursor.getString(3));
		return feed;
	}
	
	/**
	 * get feed items by feed_id.
	 * @param fid
	 * @return
	 */
	public List<Item> getFeedItems(long fid) {
		List<Item> items = new ArrayList<Item>();
		
		Cursor cursor = db.query(RssRoidSqliteHelper.TB_ITEM, 
				new String[]{"id", "title", "author"}, 
				null, null, null, null, "pub_date DESC");
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			Item item = new Item();
			item.setId(cursor.getLong(0));
			item.setTitle(cursor.getString(1));
			item.setAuthor(cursor.getString(2));
			
			items.add(item);
			cursor.moveToNext();
		}
		cursor.close();
		return items;
	}
}
