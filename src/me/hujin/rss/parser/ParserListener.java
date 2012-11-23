package me.hujin.rss.parser;

public class ParserListener implements IParserListener {

	private long lastItemTimestamp;
	
	private long lastBuildTimestamp;
	
	public ParserListener(long lastBuildTimestamp, long lastItemTimestamp) {
		this.lastItemTimestamp = lastItemTimestamp;
		this.lastBuildTimestamp = lastBuildTimestamp;
	}
	
	public boolean onMetaReceive(RSSFeed feed) {
		if(feed.getLastBuildTimestamp() <= lastBuildTimestamp) {
			return true;
		}
		return false;
	}

	public boolean onItemReceive(RSSItem item) {
		if(item.getTimestamp() <= lastItemTimestamp) {
			return true;
		}
		return false;
	}

	public void onEnd(RSSFeed feed) {
		
	}
}
