package me.hujin.rss.reader;

import java.util.List;

import me.hujin.rss.parser.FeedParser;
import me.hujin.rss.parser.ParserListener;
import me.hujin.rss.parser.RSSFeed;
import me.hujin.rss.parser.RSSItem;
import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.FeedDataSource;
import me.hujin.rss.storage.Item;
import me.hujin.rss.storage.ItemDataSource;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedListActivity extends ListActivity {

	final public static String ITEM_INFO = "item_info"; 
	
	private ItemListViewAdapter listAdapter;
	private FeedDataSource dataSource;
	private ItemDataSource itemDataSource;
	private Feed currentFeed;
	private Dialog dialog;
	private Thread thread;
	private FeedListActivity activity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feed_list);
        
        currentFeed = (Feed) getIntent().getSerializableExtra(MainActivity.FEED_INFO);
        System.out.println("list items of " + currentFeed.getTitle());
        
        TextView titleTextView = (TextView) findViewById(R.id.feed_list_title);
        titleTextView.setText(currentFeed.getTitle());
        
        init();
        
        showItemList();
    }

    protected void init() {
    	dataSource = new FeedDataSource(this);
    	itemDataSource = new ItemDataSource(this);
    	activity = this;
    }
    
    public void showItemList() {
    	dataSource.open();
    	
    	List<Item> items = dataSource.getFeedItems(currentFeed.getFid());
    	listAdapter = new ItemListViewAdapter(this, R.layout.item_list_item, items);
    	setListAdapter(listAdapter);
    	
		dataSource.close();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_feed_list, menu);
        return true;
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(FeedListActivity.this, FeedViewActivity.class);
		intent.putExtra(FeedListActivity.ITEM_INFO, listAdapter.getItem(position));
		intent.putExtra(MainActivity.FEED_INFO, currentFeed);
		startActivity(intent);
	}
    
	/**
	 * refresh feed list
	 * @param view
	 */
	public void refreshFeedList(View view) {
		
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.add_feed_progress);
		thread = new RefreshFeedThread();
		thread.start();
		//dialog.setOn
		dialog.show();
	}
	
	private class RefreshFeedThread extends Thread {
		
		public void run() {
			
			super.run();
			FeedParser parser = new FeedParser();
			parser.setParserListener(new ParserListener(currentFeed.getLastBuildDate(), currentFeed.getLastItemDate()));
			System.out.println(currentFeed.getLink());
			parser.load(currentFeed.getLink());
			parser.getFeed().printFeed();
			
			RSSFeed feed = parser.getFeed();
			if(feed.valid()) {
				if(feed.count() > 0) {
					itemDataSource.open();
					itemDataSource.saveAll(feed, currentFeed.getFid());
					itemDataSource.close();
					
					dataSource.open();
					dataSource.updateTimestamp(
							currentFeed.getFid(), 
							feed.getLastBuildTimestamp(), 
							feed.getItems().get(0).getTimestamp());
					dataSource.close();
				}
				
				activity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(activity, "Refresh Success", Toast.LENGTH_SHORT).show();
					}
				});
				
			}else {
				activity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(activity, "Error Occured", Toast.LENGTH_SHORT).show();
					}
				});
			}
			dialog.cancel();
		}
		
	}
}
