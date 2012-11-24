package me.hujin.rss.parser;

import java.util.ArrayList;
import java.util.List;

import me.hujin.rss.util.RssDateTime;

public class RSSFeed {
	
	public String title;
	public String link;
	public String description;
	public String lastBuildDate;
	
	private List<RSSItem> items = new ArrayList<RSSItem>();
	
	public RSSFeed setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public RSSFeed setLink(String link) {
		this.link = link;
		return this;
	}
	
	public RSSFeed setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public RSSFeed setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
		return this;
	}
	
	public long getLastBuildTimestamp() {
		System.out.println("lastBuildDate: " + lastBuildDate);
		return new RssDateTime(lastBuildDate).getTimestamp();
	}
	
	public int count() {
		return items.size();
	}
	
	public RSSFeed addItem(RSSItem item) {
		items.add(item);
		return this;
	}
	
	public List<RSSItem> getItems() {
		return items;
	}
	
	/**
	 * print feed information to console.
	 */
	public void printFeed() {
		System.out.println("title: " + title);
		System.out.println("desc:  " + description);
		System.out.println("last:  " + lastBuildDate);
		System.out.println("Total items is: " + items.size());
		for(int i = 0; i < items.size(); i ++) {
			System.out.println("item: " + items.get(i).getTitle());
			System.out.println("pubDate: " + items.get(i).getTimestamp());
		}
	}
	
	/**
	 * check the if the feed is valid.
	 * 
	 * @return
	 */
	public boolean valid() {
		if(title != null && description != null && lastBuildDate != null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * remove all items belongs to the feed.
	 */
	public void removeAll() {
		items.clear();
	}
	
	public long caculateLastItemTimestamp() {
		long ret = 0, t;
		for(RSSItem item: items) {
			t = item.getTimestamp();
			if(t > ret) {
				ret = t;
			}
		}
		return ret;
	}
}
