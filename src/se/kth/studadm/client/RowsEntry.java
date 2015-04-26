package se.kth.studadm.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class RowsEntry extends JavaScriptObject {
	
	protected RowsEntry(){}
	
	public final native String getId()/*-{
		return this.id;
	}-*/;
	
	public final native String getKey() /*-{ 
		return this.key; 
	}-*/;
	
	public final native String getDate() /*-{ 
		return this.value.date; 
	}-*/;
	
	public final native String getWeek() /*-{ 
		return this.value.week; 
	}-*/;
	

	public final native String getWeekNr() /*-{ 
		return this.value.week.substring(6, 8); 
	}-*/;
	
	public final native String getDayNrOfWeek() /*-{ 
		return this.value.week.substring(9);
	}-*/;
	
/*	public final native JsArray getRows()-{ 
    	return this.rows; 
	}-;
	
	public final native int getTotalRows()-{
		return this.total_rows;
	}-;

	public final native int getOffset()-{
		return this.offset;
	}-;*/
	
	
}