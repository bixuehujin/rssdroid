package me.hujin.rss.storage;

import java.io.Serializable;

public class Feed implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long fid;
	private String title;
	private String description;
	private long last_build_date;
	private long last_item_date;
	private String link;
	
	public long getFid() {
		return fid;
	}
	
	public void setFid(long fid) {
		this.fid = fid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getLastBuildDate() {
		return last_build_date;
	}
	
	public void setLastBuildDate(long last_build_date) {
		this.last_build_date = last_build_date;
	}
	
	public long getLastItemDate() {
		return last_item_date;
	}
	
	public void setLastItemDate(long last_item_date) {
		this.last_item_date = last_item_date;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
}
