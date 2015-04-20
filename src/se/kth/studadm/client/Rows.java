package se.kth.studadm.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
 
 
public class Rows extends JavaScriptObject {
 
	protected Rows() {}
 
	public final native JsArray<RowsEntry> getRows() /*-{
    	return this.rows;
  	}-*/;
 
}