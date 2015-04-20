package se.kth.studadm.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

interface JsonFace{
	
	int getTotalRows();
	int getOffset();
	JsArray getRows();
}