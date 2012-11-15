package me.hujin.rss.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.xml.sax.SAXException;


public class FeedParser {

	
	private String url;
	
	private IParserListener listener;
	
	private FeedParserHandler handler; 
	
	private SAXParser parser;
	
	private RSSFeed feed;
	
	public FeedParser() {
		url = null;
	}
	
	public FeedParser(String url) {
		this.url = url;
	}
	
	public static void main(String[] args) {

	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void init() {
			
		try {
			
			parser = SAXParserFactory.newInstance().newSAXParser();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
			
		
		if(listener == null) {
			listener = new DefaultParserListener();
		}
		handler = new FeedParserHandler(listener);
	}
	
	
	public void load() {
		init();

		try {
			parser.parse("uri", handler);
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://zhihu.com/rss");
		ClientConnectionManager manager = client.getConnectionManager();
		
		manager.shutdown();
		
		try {
			HttpResponse response = client.execute(request);
			
			InputStream stream = response.getEntity().getContent();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setParserListener(IParserListener listener) {
		this.listener = listener;
	}
}
