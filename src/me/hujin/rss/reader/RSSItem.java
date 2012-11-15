package me.hujin.rss.reader;


/**
 * present a single rss item.
 * 
 * @author Jin Hu <bixuehujin@gmail.com>
 */

public class RSSItem {
	
	private String title;
	private String link;
	private String description;
	private String pubDate;
	private String creator;
	
	public RSSItem(String title, String link, 
			String description, String pubDate, String creator) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.creator = creator;
	}
	
	public RSSItem(){
		
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public RSSItem setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public RSSItem getLink(String link) {
		this.link = link;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public RSSItem setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getPubDate() {
		return pubDate;
	}

	public RSSItem setPubDate(String pubDate) {
		this.pubDate = pubDate;
		return this;
	}

	public String getCreator() {
		return creator;
	}

	public RSSItem setCreator(String creator) {
		this.creator = creator;
		return this;
	}
	
	public int getTimestamp() {
		if(pubDate == null) {
			return 0;
		}
		
		return 0;
	}
}
