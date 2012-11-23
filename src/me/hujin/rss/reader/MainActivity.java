package me.hujin.rss.reader;

import java.util.List;

import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.FeedDataSource;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	public final static String FEED_INFO = "feed_info";
	
	private FeedDataSource dataSource;
	
	private FeedListViewAdapter listAdapter;
	
	private MainActivity activity = this;
	
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
        
        dataSource.close();
        
        getListView().setOnItemLongClickListener(new OnListItemLongClickListener());
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
	
	private class OnListItemLongClickListener implements OnItemLongClickListener{

		Dialog dialog;
		
		Feed deletingFeed;
		
		public boolean onItemLongClick(AdapterView parent, View view,
				int position, long id) {

			dialog = new Dialog(activity);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_feed_operation);
			
			deletingFeed = listAdapter.getItem(position);
			
			ListView listView = (ListView) dialog.findViewById(R.id.list_feed_operations);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, 
					android.R.layout.simple_list_item_1, 
					new String[]{"Delete", "Show Info"});
			
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnFeedOperationListItemClickListener());
			dialog.show();
			
			return true;
		}
		
		private class OnFeedOperationListItemClickListener implements OnItemClickListener {

			public void onItemClick(AdapterView parent, View view, int position,
					long id) {
				switch (position) {
				case 0:
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setTitle("Are you sure to delete the feed?")
						.setPositiveButton("Confirm", new OnDialogButtonClickListener())
						.setNegativeButton("Cancel", new OnDialogButtonClickListener())
						.setMessage("This will delete all items belongs to the feed.");
					
					AlertDialog alert = builder.create();
					alert.show();
					break;

				default:
					break;
				}
				dialog.cancel();
			}
			
		}
		
		private class OnDialogButtonClickListener implements DialogInterface.OnClickListener {

			public void onClick(DialogInterface dialog, int which) {

				if(which == AlertDialog.BUTTON_POSITIVE) {
					dataSource.open();
					if(dataSource.delete(deletingFeed.getFid())) {
						Toast.makeText(activity, "Delete Feed Success", Toast.LENGTH_SHORT).show();
						listAdapter.remove(deletingFeed);
						listAdapter.notifyDataSetChanged();
					}else {
						Toast.makeText(activity, "Delete Feed Failed", Toast.LENGTH_SHORT).show();
					}
					dataSource.close();
				}
				
			}
			
		}
	}

}
