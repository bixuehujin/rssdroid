package me.hujin.rss.reader;

import me.hujin.rss.storage.Feed;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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
        return true;
    }
    
    public void backToHome(View view) {
    	Intent intent = new Intent(FeedInfoActivity.this, MainActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    }
}
