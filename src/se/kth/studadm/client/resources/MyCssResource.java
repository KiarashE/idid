package se.kth.studadm.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface MyCssResource extends CssResource{
	
	@ClassName("caltable")
	String getCaltable();

	@ClassName("datepicker")
	String getDatepicker();

	@ClassName("totdayhours")
	String getDayhours();

	@ClassName("lunchbox")
	String getLunchbox();

	@ClassName("timebox")
	String getTimebox();
}
