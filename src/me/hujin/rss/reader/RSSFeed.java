package me.hujin.rss.reader;

import java.util.List;

public class RSSFeed {

	private List items;
	
	private String url;
	
	public RSSFeed() {
		url = null;
	}
	
	public RSSFeed(String url) {
		this.url = url;
	}
	
	public boolean loadAll() {
		
		return true;
	}
	
	public void loadCallback() {
		
	}
	
	public void setFeedListener() {
		
	}
}


interface IFeedListener {
	public boolean onMetaReceive();
	public boolean onItemReceive(RSSItem item);
}
