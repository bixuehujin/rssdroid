package me.hujin.rss.reader;

import me.hujin.rss.parser.FeedParser;
import me.hujin.rss.parser.RSSFeed;
import me.hujin.rss.reader.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class AddFeedActivity extends Activity {

	public EditText feedName;
	public EditText feedUrl;
	public Button addFeedButton;
	public AddFeedActivity self;
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
	
	
	public AddFeedActivity() {
		this.self = this;
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
			parser.load("http://www.zhihu.com/rss");
			parser.getFeed().printFeed();
			
			dialog.cancel(); //close when operation completed.
		}
    }
}


