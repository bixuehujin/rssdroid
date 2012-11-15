package me.hujin.rss.parser;

import java.util.ArrayList;
import java.util.List;

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
	
	public int getLastBuildTimestamp() {
		return 0;
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
}
