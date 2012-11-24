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
	
	private FeedParser parser;
	
	public FeedParserHandler(FeedParser context, IParserListener listener) {
		this.listener = listener;
		feed = new RSSFeed();
		this.parser = context;
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
				if(listener.onMetaReceive(feed)) {
					parser.stop();
				}
			}
			isItem = true;
			item = new RSSItem();
		}
		this.tagName = qName;
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(qName.equals("item")) {
			feed.addItem(item);
			if(listener.onItemReceive(item)) {
				parser.stop();
			}
		}
		tagName = null;
	}
	
	public void endDocument() throws SAXException {
		listener.onEnd(feed);
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
				item.appendDescription(str);
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
			if(isItem) {
				item.setPubDate(str);
			}
		}else if(tagName.equals("creator")) {
			item.setCreator(str);
		}else if(tagName.equals("content:encoded")) {
			if(isItem) {
				item.appendContent(str);
			}
		}
 	}
}
