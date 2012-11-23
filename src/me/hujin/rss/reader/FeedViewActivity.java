package me.hujin.rss.reader;

import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.Item;
import me.hujin.rss.storage.ItemDataSource;
import android.app.Activity;
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
    	
    	webView.loadData(item.getContentExt(), "text/html; charset=UTF-8", "UTF-8");
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_feed_view, menu);
        return true;
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dataSource.close();
	}

    public void showFeedItemInfo(View view) {
    	
    }
}