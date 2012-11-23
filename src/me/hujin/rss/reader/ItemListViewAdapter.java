package me.hujin.rss.reader;

import java.util.ArrayList;
import java.util.List;

import me.hujin.rss.storage.Feed;
import me.hujin.rss.storage.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ItemListViewAdapter extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> items;
	
	public ItemListViewAdapter(Context context, int textViewResourceId, List<Item> values) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.items = (ArrayList<Item>) values;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list_item, null);
		}
		
		Item item = items.get(position);
		if(item != null) {
			TextView titleView = (TextView)view.findViewById(R.id.item_title);
			titleView.setText(item.getTitle());
		}
		
		return view;
		
	}
}
