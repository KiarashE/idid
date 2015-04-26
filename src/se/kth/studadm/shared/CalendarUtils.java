package se.kth.studadm.shared;

public class CalendarUtils {
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeekdate() {
		return weekdate;
	}
	public void setWeekdate(String weekdate) {
		this.weekdate = weekdate;
	}
	public String date;
	public String weekdate;
	
	public String getDayOfWeek(String dayNr){
		String day = null;
		if(dayNr.equalsIgnoreCase("1"))
			day = "Måndag";
		else if(dayNr.equalsIgnoreCase("2"))
			day = "Tisdag";
		else if(dayNr.equalsIgnoreCase("3"))
			day = "Onsdag";
		else if(dayNr.equalsIgnoreCase("4"))
			day = "Torsdag";
		else if(dayNr.equalsIgnoreCase("5"))
			day = "Fredag";
		else if(dayNr.equalsIgnoreCase("6"))
			day = "Lördag";
		else if(dayNr.equalsIgnoreCase("7"))
			day = "Söndag";
			
		return day;
	}
	

}
