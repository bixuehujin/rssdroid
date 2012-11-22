package me.hujin.rss.reader;

import java.util.ArrayList;
import java.util.List;
import me.hujin.rss.storage.Feed;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class FeedListViewAdapter extends ArrayAdapter<Feed> {

	private Context context;
	private ArrayList<Feed> feeds;
	
	public FeedListViewAdapter(Context context, int textViewResourceId, List<Feed> values) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.feeds = (ArrayList<Feed>) values;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.feed_list_item, null);
		}
		
		Feed item = feeds.get(position);
		if(item != null) {
			TextView titleView = (TextView)view.findViewById(R.id.feed_item_name);
			titleView.setText(item.getTitle());
			
			TextView countView = (TextView)view.findViewById(R.id.feed_item_unread);
			//TODO change to unread count
			countView.setText("2");
		}
		
		return view;
		
	}
}
