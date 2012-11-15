package me.hujin.rss.reader;

import android.content.Context;
import android.widget.ArrayAdapter;


public class FeedListViewAdapter extends ArrayAdapter<String> {

	public FeedListViewAdapter(Context context, int textViewResourceId, String[] values) {
		super(context, textViewResourceId, values);

	}
	
	public int getCount() {
		return super.getCount();
	}
}
