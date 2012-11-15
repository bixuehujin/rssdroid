package me.hujin.rss.parser;

public interface IParserListener {
	
	public boolean onMetaReceive(RSSFeed feed);
	
	public boolean onItemReceive(RSSItem item);
	
	public void onEnd(RSSFeed feed);
}
