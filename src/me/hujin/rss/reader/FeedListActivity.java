package me.hujin.rss.reader;

import me.hujin.rss.storage.Feed;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class FeedListActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feed_list);
        
        Feed feed = (Feed) getIntent().getSerializableExtra(MainActivity.FEED_INFO);
        System.out.println("list items of " + feed.getTitle());
        
        TextView titleTextView = (TextView) findViewById(R.id.feed_list_title);
        titleTextView.setText(feed.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_feed_list, menu);
        return true;
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		
	}

    
}
