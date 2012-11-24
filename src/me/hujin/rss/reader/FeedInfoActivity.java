package me.hujin.rss.reader;

import me.hujin.rss.storage.Feed;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class FeedInfoActivity extends Activity {

	private Feed currentFeed;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feed_info);
        
        init();
    }

    protected void init() {
    	currentFeed = (Feed) getIntent().getSerializableExtra(MainActivity.FEED_INFO);
    	TextView titleText = (TextView)findViewById(R.id.title_feed_info);
    	titleText.setText(currentFeed.getTitle());
    	
    	TextView feedTitleText = (TextView)findViewById(R.id.feed_info_title);
    	feedTitleText.setText(currentFeed.getTitle());
    	
    	TextView feedDescText = (TextView)findViewById(R.id.feed_info_description);
    	feedDescText.setText(currentFeed.getDescription());
    	
    	TextView feedLinkText = (TextView)findViewById(R.id.feed_info_link);
    	feedLinkText.setText(currentFeed.getLink());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_feed_info, menu);
        return true;
    }
    
    public void backToHome(View view) {
    	finish();
    }
}
