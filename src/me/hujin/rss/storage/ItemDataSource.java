package me.hujin.rss.storage;

import java.util.ArrayList;
import java.util.List;

import me.hujin.rss.parser.RSSFeed;
import me.hujin.rss.parser.RSSItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ItemDataSource extends DataSourceBase{

	
	public ItemDataSource(Context context) {
		super(context);
	}


	public Item save(RSSItem rssItem) {
		
		Item item = new Item();
		return item;
	}
	
	
	public List<Item> saveAll(RSSFeed feed, long fid) {
		
		List<Item> items= new ArrayList<Item>();
		List<RSSItem> rssItems = feed.getItems();
		for(int i = 0; i < rssItems.size(); i ++) {
			Item item = new Item();
			RSSItem rssItem = rssItems.get(i);
			ContentValues values = new ContentValues();
			values.put("title", rssItem.getTitle());
			values.put("description", rssItem.getDescription());
			values.put("author", rssItem.getCreator());
			values.put("pub_date", rssItem.getTimestamp());
			values.put("link", rssItem.getLink());
			values.put("content", "");
			values.put("fid", fid);
			
			
			long id = db.insert("item", null, values);
			item.setAuthor(rssItem.getCreator());
			item.setTitle(rssItem.getTitle());
			item.setDescription(rssItem.getDescription());
			item.setLink(rssItem.getLink());
			item.setFid(fid);
			item.setId(id);
			
			items.add(item);
		}
		
		return items;
	}
	
	
	public Item getItem(long id) {
		Item item = new Item();
		
		Cursor cursor = db.query("item", 
				new String[]{"id", "fid", "title", "description", "content", "author", "pub_date", "link"}, 
				"id=" + id, null, null, null, null);
		
		cursor.moveToFirst();
		
		if(!cursor.isAfterLast()) {
			item.setId(cursor.getLong(0));
			item.setFid(cursor.getLong(1));
			item.setTitle(cursor.getString(2));
			item.setDescription(cursor.getString(3));
			item.setContent(cursor.getString(4));
			item.setAuthor(cursor.getString(5));
			item.setPubDate(cursor.getLong(6));
			item.setLink(cursor.getString(7));
		}
		
		cursor.close();
		
		return item;
	}
	
	//public String 
}
