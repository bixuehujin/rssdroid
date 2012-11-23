/**
 * AbsFeedParserListener class file.
 * 
 * @author Jin Hu <bixuehujin@gmail.com>
 */

package me.hujin.rss.parser;


/**
 * Abstract FeedParserListener used to receive event on parser rss.
 *
 */
public class DefaultParserListener implements IParserListener{
	
	
	public boolean onItemReceive(RSSItem item) {
		
		return false;
	}

	public boolean onMetaReceive(RSSFeed feed) {
		
		return false;
	}
	
	public void onEnd(RSSFeed feed) {
		
	}
}
