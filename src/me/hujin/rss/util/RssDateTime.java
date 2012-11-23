package me.hujin.rss.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssDateTime {
	
	private String rawDateTime = null;
	
	private long timestamp;
	
	static private String rowDateTimeFormat = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	static private String outputDateTimeFormat = "yyyy HH:mm:ss";
	
	static private SimpleDateFormat inFormat = new SimpleDateFormat(rowDateTimeFormat, Locale.US);
	
	static private SimpleDateFormat outFormat = new SimpleDateFormat(outputDateTimeFormat);
	
	
	public RssDateTime(String rawDateStr) {
		this.rawDateTime = rawDateStr;
	}
	
	public RssDateTime(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getTimestamp() {
		if(rawDateTime == null) {
			return timestamp;
		}else {
			return getTimestampFromString(rawDateTime);
		}
	}
	
	private long getTimestampFromString(String str) {
		long timestamp = 0;
		try {
			Date date = inFormat.parse(str);
			timestamp = date.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
	
	private Date getDate() {
		if(rawDateTime != null) {
			try {
				return inFormat.parse(rawDateTime);
			} catch (ParseException e) {
				return null;
			}
		}
	
		Date date = new Date();
		date.setTime(timestamp * 1000);
		return date;
	}
	
	public String getFormattedDateTime() {
		Date date = getDate();
		return outFormat.format(date);
	}
}
