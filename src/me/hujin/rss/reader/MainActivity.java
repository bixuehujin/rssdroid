package me.hujin.rss.reader;

import me.hujin.rss.reader.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	static String[] values = new String[] {"Linux", "PHP"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        //ListView feedListView = (ListView) findViewById(R.id.feed_list);
        FeedListViewAdapter feedListAdapter = new FeedListViewAdapter(this, android.R.layout.simple_list_item_1, values);
        
        //feedListView.setAdapter(feedListAdapter);
        System.out.println("start");
        setListAdapter(feedListAdapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    }
    
    public void onAddFeedClick(View v) {
    	Intent intent = new Intent(this, AddFeedActivity.class);
    	startActivity(intent);
    }
}
