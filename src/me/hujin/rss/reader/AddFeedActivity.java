package me.hujin.rss.reader;

import me.hujin.rss.parser.FeedParser;
import me.hujin.rss.parser.RSSFeed;
import me.hujin.rss.reader.R;
import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.FeedDataSource;
import me.hujin.rss.storage.ItemDataSource;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFeedActivity extends Activity {

	public final static int RESULT_CODE = 1;
	
	public EditText feedName;
	
	public EditText feedUrl;
	
	public Button addFeedButton;
	
	public AddFeedActivity activity;
	/**
	 * Dialog object for show progress bar.
	 */
	public Dialog dialog;
	
	public FeedParser parser;
	/**
	 * thread to fetch rss resources.
	 */
	private Thread thread;
	/**
	 * handler for post/receive messages.
	 */
	private Handler handler;
	
	private FeedDataSource feedDataSource;
	
	private ItemDataSource itemDataSource;
	
	
	public AddFeedActivity() {
		this.activity = this;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_feed);
        
        feedName =(EditText) findViewById(R.id.feed_name);
        feedUrl = (EditText) findViewById(R.id.feed_url);
        addFeedButton = (Button) findViewById(R.id.add_feed_btn);
        
        addFeedButton.setOnClickListener(new OnAddFeedButtonClickListener());
        
        feedDataSource = new FeedDataSource(this);
        itemDataSource = new ItemDataSource(this);
        
        createProgressDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_feed, menu);
        return true;
    }

    /**
     * Create a Dialog object used to show a ProgressBar indicate the progress 
     * of RSS fetching.
     */
    public void createProgressDialog() {
    	if(dialog == null) {
    		dialog = new Dialog(this);
    		dialog.setOnCancelListener(new OnDialogCancelListener());
    		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      		dialog.setContentView(R.layout.add_feed_progress);
      		
      		//handler = new Handler();
    	}
    	
    }
    
    public void createThread() {
    	thread = new FetchThread("fetch thread");
    	thread.start();
    }
    
    /**
     * back to home button pressed. 
     * @param view
     */
    public void backToHome(View view) {
    	finish();
    }
    
    /**
     * Listener for Add Feed button onClick event.
     */
    private class OnAddFeedButtonClickListener implements OnClickListener{
    	
    	public void onClick(View v) {
    		String name = feedName.getText().toString();
            String url = feedUrl.getText().toString();
            
            if(url.trim().equals("")) {
            	feedUrl.requestFocus();
            	return;
            }
            
            System.out.println(name);
            System.out.println(url);
            
            createThread();
            dialog.show();
    	}
    }
    
    
    private class OnDialogCancelListener implements DialogInterface.OnCancelListener {

		public void onCancel(DialogInterface dialog) {
			thread.destroy();
			System.out.println("cancel dialog");
		}
    }
    
    
    private class FetchThread extends Thread{

    	public FetchThread(String name) {
			super(name);
		}

		/**
    	 * destroy the thread and stop parse rss.
    	 * @Override
    	 */
		public void destroy() {
			parser.stop();
		}

		/**
		 * start a thread and fetch rss document form remote server.
		 * @Override
		 */
		public void run() {
			super.run();
			
			parser = new FeedParser();
			parser.load(feedUrl.getText().toString());
			
			RSSFeed feed = parser.getFeed();
			if(feed.valid()) {
				if(!feedName.getText().toString().trim().equals("")) {
					feed.setTitle(feedName.getText().toString().trim());
				}
				feedDataSource.open();
				Feed newFeed = feedDataSource.addFeed(feed);
				
				
				itemDataSource.open();
				itemDataSource.saveAll(feed, newFeed.getFid());
				
				feedDataSource.updateTimestamp(newFeed.getFid(), 0, feed.getItems().get(0).getTimestamp());
				
				itemDataSource.close();
				feedDataSource.close();
				activity.runOnUiThread(new Runnable() {
					
					public void run() {
						Toast.makeText(activity, "Add Feed Success", Toast.LENGTH_SHORT).show();
					}
				});
				
				//send data to main activity.
				Intent i = new Intent();
				i.putExtra("new_feed", newFeed);
				setResult(RESULT_CODE, i);
				
				
				activity.finish();
			}else {
				activity.runOnUiThread(new Runnable() {
					
					public void run() {
						Toast.makeText(activity, "Add Feed Failed", Toast.LENGTH_SHORT).show();
					}
				});
			}
			
			dialog.cancel(); //close when operation completed.
		}
    }
}


