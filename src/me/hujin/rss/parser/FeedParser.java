package me.hujin.rss.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


public class FeedParser {

	
	private IParserListener listener;
	
	private FeedParserHandler handler; 
	
	private SAXParser parser;
	
	private ClientConnectionManager connManager;
	
	
	public void init() {
		if(parser != null) {
			return;
		}
		
		if(listener == null) {
			listener = new DefaultParserListener();
		}
		handler = new FeedParserHandler(listener);
	}
	
	
	public void load(String url) {
		init();
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		connManager = client.getConnectionManager();
		try {
			HttpResponse response = client.execute(request);
			
			InputStream stream = response.getEntity().getContent();
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			try {
				parser = factory.newSAXParser();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			XMLReader xmlreader = parser.getXMLReader();
			xmlreader.setContentHandler(handler);
			InputSource is = new InputSource(stream);
			xmlreader.parse(is);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * set a custom parser listener.
	 * 
	 * @param listener a listener implement IParserListener Interface.
	 */
	public void setParserListener(IParserListener listener) {
		this.listener = listener;
	}
	
	/**
	 * stop feed parse and disconnect http connection.
	 */
	public void stop() {
		connManager.shutdown();
	}
	
	/**
	 * get rss feed object.
	 */
	public RSSFeed getFeed() {
		return handler.getFeed();
	}
}
