package me.hujin.rss.reader;

import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.Item;
import me.hujin.rss.storage.ItemDataSource;
import me.hujin.rss.util.RssDateTime;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

public class FeedViewActivity extends Activity {

	private ItemDataSource dataSource;
	
	private Item currentItem;
	
	private Feed currentFeed;
	
	private WebView webView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feed_view);
        
        init();
    }

    protected void init() {
    	currentItem = (Item) getIntent().getSerializableExtra(FeedListActivity.ITEM_INFO);
    	currentFeed = (Feed) getIntent().getSerializableExtra(MainActivity.FEED_INFO);
    	
    	TextView titleTextView = (TextView) findViewById(R.id.feed_view_title);
    	titleTextView.setText(currentFeed.getTitle());
    	
    	dataSource = new ItemDataSource(this);
    	dataSource.open();
    	
    	Item item = dataSource.getItem(currentItem.getId());
    	webView = (WebView) findViewById(R.id.feed_view);
    	webView.getSettings().setDefaultTextEncodingName("UTF-8");

    	webView.loadDataWithBaseURL(null, 
    			renderWebViewData(item),
    			"text/html", "utf-8", null);
    	dataSource.close();
    }
    
    protected String renderWebViewData(Item item) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("<html><body>");
    	
    	String header = "<h3><a href='" + item.getLink() + "'>" + item.getTitle() + "</a></h3>"
    			+ "<hr>" 
    			+ "From " + currentFeed.getTitle() 
    			+ " on " + new RssDateTime(item.getPubDate()).getFormattedDateTime();
    	
    	buffer.append("<div style='margin-bottom:5px' class='feed-head'>" + header + "</div>");
    	buffer.append("<div class='feed-content'>" + item.getContentExt() + "</div>");
    	buffer.append("</body></html>");
    	return buffer.toString();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

    public void showFeedItemInfo(View view) {
    	
    }
    
    public void backToHome(View view) {
    	Intent intent = new Intent(FeedViewActivity.this, MainActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    }
}
