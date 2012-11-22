package me.hujin.rss.reader;

import java.util.List;

import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.FeedDataSource;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	public final static String FEED_INFO = "feed_info";
	
	private FeedDataSource dataSource;
	private FeedListViewAdapter listAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        //ListView feedListView = (ListView) findViewById(R.id.feed_list);
        
        
        dataSource = new FeedDataSource(this);
        dataSource.open();
        
        List<Feed> values = dataSource.getAllFeeds();
        listAdapter = new FeedListViewAdapter(this, R.layout.feed_list_item, values);
        System.out.println("start");
        setListAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	System.out.println("opening " + listAdapter.getItem(position).getLink() + "...");
    	
    	Intent intent = new Intent(MainActivity.this, FeedListActivity.class);
    	intent.putExtra(FEED_INFO, listAdapter.getItem(position));
    	
    	startActivity(intent);
    }
    
    /**
     * start AddFeedActivity 
     * @param view
     */
    public void onAddFeedClick(View view) {
    	Intent intent = new Intent(MainActivity.this, AddFeedActivity.class);
    	startActivity(intent);
    }
    

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		System.out.println("post resume");
	}
}
