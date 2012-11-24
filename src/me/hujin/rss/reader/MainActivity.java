package me.hujin.rss.reader;

import java.util.List;

import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.FeedDataSource;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	public final static String FEED_INFO = "feed_info";
	
	public final static int REQUEST_CODE = 1;
	
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
    	//startActivity(intent);
    	startActivityForResult(intent, REQUEST_CODE);
    }
    

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == AddFeedActivity.RESULT_CODE) {
			Feed newFeed = (Feed) data.getSerializableExtra("new_feed");
			if(newFeed != null) {
				listAdapter.add(newFeed);
				listAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		System.out.println("post resume");
	}
	
	private class OnListItemLongClickListener implements OnItemLongClickListener{

		Dialog dialog;
		
		Feed operatingFeed;
		
		EditText renameEditText;
		
		public boolean onItemLongClick(AdapterView parent, View view,
				int position, long id) {

			dialog = new Dialog(activity);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_feed_operation);
			
			operatingFeed = listAdapter.getItem(position);
			
			ListView listView = (ListView) dialog.findViewById(R.id.list_feed_operations);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, 
					android.R.layout.simple_list_item_1, 
					new String[]{"Delete Feed", "Rename Feed", "Item Info"});
			
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnFeedOperationListItemClickListener());
			dialog.show();
			
			return true;
		}
		
		private class OnFeedOperationListItemClickListener implements OnItemClickListener {

			public void onItemClick(AdapterView parent, View view, int position,
					long id) {
				switch (position) {
				//delete feed
				case 0:
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setTitle("Are you sure to delete the feed?")
						.setPositiveButton("Confirm", new OnDialogButtonClickListener(OnDialogButtonClickListener.ACTION_DELETE))
						.setNegativeButton("Cancel", new OnDialogButtonClickListener())
						.setMessage("This will delete all items belongs to the feed.");
					
					AlertDialog alert = builder.create();
					alert.show();
					break;
				//rename feed
				case 1:
					
					renameEditText = new EditText(activity);
					renameEditText.setText(operatingFeed.getTitle());
					CharSequence text = renameEditText.getText();
					if(text instanceof Spannable) {
						 Spannable spanText = (Spannable)text;
						 Selection.setSelection(spanText, text.length());
					}
					
					
					AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
					builder1.setTitle("Rename Feed")
						.setPositiveButton("Confirm", new OnDialogButtonClickListener(OnDialogButtonClickListener.ACTION_RENAME))
						.setNegativeButton("Cancel", new OnDialogButtonClickListener())
						.setView(renameEditText);
					AlertDialog alert1 = builder1.create();
					alert1.show();
					break;
				default:
					break;
				}
				dialog.cancel();
			}
			
		}
		
		private class OnDialogButtonClickListener implements DialogInterface.OnClickListener {

			final public static int ACTION_NONE = 0;
			final public static int ACTION_DELETE = 1;
			final public static int ACTION_RENAME = 2;
			
			private int action;
			
			public OnDialogButtonClickListener() {
				this.action = ACTION_NONE;
			}
			
			public OnDialogButtonClickListener(int action) {
				this.action = action;
			}
			
			public void onClick(DialogInterface dialog, int which) {

				if(which == AlertDialog.BUTTON_POSITIVE) {
					if(action == ACTION_DELETE) {
						doActionDelete();
					}else if(action == ACTION_RENAME) {
						doActionRename();
					}
				}
				
			}
			
			private void doActionDelete() {
				dataSource.open();
				if(dataSource.delete(operatingFeed.getFid())) {
					Toast.makeText(activity, "Delete Feed Success", Toast.LENGTH_SHORT).show();
					listAdapter.remove(operatingFeed);
					listAdapter.notifyDataSetChanged();
				}else {
					Toast.makeText(activity, "Delete Feed Failed", Toast.LENGTH_SHORT).show();
				}
				dataSource.close();
			}
			
			private void doActionRename() {
				String newName = renameEditText.getText().toString().trim();
				if(newName.equals("")) {
					Toast.makeText(activity, "Name Cannot be Blank", Toast.LENGTH_SHORT).show();
					return;
				}
				dataSource.open();
				if(dataSource.rename(operatingFeed.getFid(), newName)) {
					Toast.makeText(activity, "Rename Feed Success", Toast.LENGTH_SHORT).show();
					operatingFeed.setTitle(newName);
					listAdapter.notifyDataSetChanged();
				}else {
					Toast.makeText(activity, "Rename Feed Failed", Toast.LENGTH_SHORT).show();
				}
				
				dataSource.close();
			}
			
		}
	}

}
