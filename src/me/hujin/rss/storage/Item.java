package me.hujin.rss.storage;

import java.io.Serializable;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private long fid;
	private String title;
	private String description;
	private String content;
	private String author;
	private long pub_date;
	private String link;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public long getPubDate() {
		return pub_date;
	}
	
	public void setPubDate(long pub_date) {
		this.pub_date = pub_date;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getContentExt() {
		if(content != null && !content.equals("")) {
			return content;
		}else {
			return description;
		}
	}
}
