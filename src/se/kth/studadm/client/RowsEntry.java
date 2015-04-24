package se.kth.studadm.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;

public class RowsEntry extends JavaScriptObject {
	
	protected RowsEntry(){}
	
	public final native String getId()/*-{
		return this.id;
	}-*/;
	
	public final native String getKey() /*-{ 
		return this.key; 
	}-*/;
	
	public final native JavaScriptObject getValue() /*-{ 
	return this.value; 
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