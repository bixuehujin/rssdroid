package me.hujin.rss.util;

import me.hujin.rss.reader.R;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class AppUtil {
	
	private Activity activity;
	
	public AppUtil(Activity activity){
		this.activity = activity;
	}
	
	public boolean checkConnectify() {
		ConnectivityManager manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if(info != null && info.isConnected()) {
			return true;
		}
		Toast.makeText(activity, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
		return false;
	}
}
