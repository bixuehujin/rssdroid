package me.hujin.rss.reader;

import java.util.ArrayList;
import java.util.List;

import me.hujin.rss.parser.FeedParser;
import me.hujin.rss.parser.RSSFeed;
import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.FeedDataSource;
import me.hujin.rss.storage.Item;
import me.hujin.rss.storage.ItemDataSource;
import me.hujin.rss.util.AppUtil;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
	private FeedListActivity activity = this;
	
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
    
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_back_home:
			finish();
			break;
		case R.id.menu_show_feed_info:
			Intent intent = new Intent(FeedListActivity.this, FeedInfoActivity.class);
			intent.putExtra(MainActivity.FEED_INFO, currentFeed);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * refresh feed list
	 * @param view
	 */
	public void refreshFeedList(View view) {
		if(!new AppUtil(activity).checkConnectify()) {
			return;
		}
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.add_feed_progress);
		thread = new RefreshFeedThread();
		thread.start();
		//dialog.setOn
		dialog.show();
	}
	
	public void backToHome(View view) {
		finish();
	}
	
	private class RefreshFeedThread extends Thread {
		
		public void run() {
			
			super.run();
			
			FeedParser parser = new FeedParser(activity);
			parser.setLastBuildDate(currentFeed.getLastBuildDate())
				.setLastItemDate(currentFeed.getLastItemDate());
			
			Log.d(FeedListActivity.class.toString(), 
					"Load data from internet. lastBuildDate: " 
					+ currentFeed.getLastBuildDate() + " lastItemDate: " 
					+ currentFeed.getLastItemDate());
			
			parser.load(currentFeed.getLink());
			parser.getFeed().printFeed();
			
			RSSFeed feed = parser.getFeed();
			
			List<Item> newItems = new ArrayList<Item>();
			if(feed.valid()) {
				if(feed.count() > 0) {
					itemDataSource.open();
					newItems = itemDataSource.saveAll(feed, currentFeed.getFid());
					itemDataSource.close();
					
					dataSource.open();
					dataSource.updateTimestamp(
							currentFeed.getFid(), 
							feed.getLastBuildTimestamp(), 
							feed.caculateLastItemTimestamp());
					dataSource.close();
				}
				
				if(feed.count() > 0) {
					activity.runOnUiThread(new AddNewItemsThread(newItems));
					activity.runOnUiThread(new TaostMessageThread(feed.count() + getString(R.string.toast_n_items_updated)));
				}else {
					activity.runOnUiThread(new TaostMessageThread(getString(R.string.toast_no_update_avaliable)));
				}
				
			}else {
				activity.runOnUiThread(new TaostMessageThread(getString(R.string.toast_refresh_failed)));
			}
			dialog.cancel();
		}
		
	}
	
	private class TaostMessageThread implements Runnable {
		
		private String message;
		
		public TaostMessageThread(String message) {
			this.message = message;
		}
		
		public void run() {
			Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private class AddNewItemsThread implements Runnable {
		
		private List<Item> newItems;
		
		public AddNewItemsThread(List<Item> newItems) {
			this.newItems = newItems;
		}
		
		public void run() {
			for(int i = newItems.size() - 1; i >= 0 ; i --) {
				listAdapter.insert(newItems.get(i), 0);
			}
			listAdapter.notifyDataSetChanged();
		}
		
	}
}
