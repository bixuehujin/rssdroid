package me.hujin.rss.parser;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FeedParserHandler extends DefaultHandler {

	public RSSFeed feed;
	
	private String tagName;
	private boolean isItem = false;
	
	private RSSItem item;
	
	private IParserListener listener;
	
	public FeedParserHandler(IParserListener listener) {
		this.listener = listener;
		feed = new RSSFeed();
	}
	
	public RSSFeed getFeed() {
		return feed;
	}
	
	public void startDocument() throws SAXException{
		
	}
	
	public void startElement(String uri, String localName, 
			String qName, Attributes attrs) {
		if(qName.equals("item")) {
			if(!isItem) {
				listener.onMetaReceive(feed);
			}
			isItem = true;
			item = new RSSItem();
		}
		this.tagName = qName;
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(qName.equals("item")) {
			feed.addItem(item);
			listener.onItemReceive(item);
		}
		tagName = null;
	}
	
	public void endDocument() throws SAXException {
		
	}
	
	public void characters(char[] ch, int start, int len) throws SAXException{
		if(tagName == null) {
			return;
		}
		
		String str = new String(ch, start, len);
		if(tagName.equals("title")) {
			if(isItem){ 
				item.setTitle(str);
			}else{
				feed.setTitle(str);
			}
		}else if(tagName.equals("description")) {
			if(isItem) {
				item.setDescription(str);
			}else {
				feed.setDescription(str);
			}
		}else if(tagName.equals("link")) {
			if(isItem) {
				item.setLink(str);
			}else {
				feed.setLink(str);
			}
		}else if(tagName.equals("lastBuildDate")) {
			feed.setLastBuildDate(str);
		}else if(tagName.equals("pubDate")) {
			item.setPubDate(str);
		}else if(tagName.equals("creator")) {
			item.setCreator(str);
		}
	}
}
