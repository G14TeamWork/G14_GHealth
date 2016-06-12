package Entities;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AppointmentTimeValues {

	private LocalDateTime disSetDateTime;
	private LocalDateTime appDateTime;
	private LocalDateTime realAppDateTime;
	private long fromDisToAppDateDiffInMinutes;
	private long fromDisToRealAppDateDiffInMinutes;
	
	/** 
	 * (non-Javadoc)
	 * 

	 * days = allTimeInMinutes/24*60;
	 * hours = (allTimeInMinutes%24*60)/60;
	 * Minutes = allTimeInMinutes%60;
	 * 
	 * 
	 * 
	 * String date = new SimpleDateFormat("yyyyMMdd").format(x3); //format for update mySql workbench
	 * 
	 * 
	 * 
	 * 
	 * 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dateTime1= LocalDateTime.parse(x1+" "+y1, formatter); //convert (Date and Time) to LocalDateTime ->>> "yyyy-MM-dd HH:mm:ss"
		LocalDateTime dateTime2= LocalDateTime.parse(x2+" "+y2, formatter);
	
	 * long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes(); // diff dateTime1 - dateTime2
	 * 
	 * 
	 * 
	
	public AppointmentTimeValues() {
		this.disSetDateTime = disSetDateTime;
		this.appDateTime = appDateTime;
		this.realAppDateTime = realAppDateTime;
	}
	*/
	
	
								//[ dispatcher	seted DateTime] [ Appointment DateTime		] [ Appointment End Time ] [ Appointment realStart ,realEnd  ]
	public AppointmentTimeValues(Date setedDate, Time setedTime, 	Date appDate,Time appStartTime, 	Time appEndTime,			 Time realStartTime, Time realEndTime) 
	{
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		this.disSetDateTime = LocalDateTime.parse(setedDate+" "+setedTime, formatter); //convert (Date and Time) to LocalDateTime ->>> "yyyy-MM-dd HH:mm:ss"
		this.appDateTime = LocalDateTime.parse(appDate+" "+appStartTime, formatter);
		this.realAppDateTime = LocalDateTime.parse(appDate+" "+realStartTime, formatter);
		this.fromDisToAppDateDiffInMinutes = java.time.Duration.between(appDateTime, disSetDateTime).toMinutes();
		this.fromDisToRealAppDateDiffInMinutes = java.time.Duration.between(realAppDateTime, appDateTime).toMinutes();
	
	
	}
	public LocalDateTime getDisSetDateTime() {
		return disSetDateTime;
	}
	public void setDisSetDateTime(LocalDateTime disSetDateTime) {
		this.disSetDateTime = disSetDateTime;
	}
	public LocalDateTime getAppDateTime() {
		return appDateTime;
	}
	public void setAppDateTime(LocalDateTime appDateTime) {
		this.appDateTime = appDateTime;
	}
	public LocalDateTime getRealAppDateTime() {
		return realAppDateTime;
	}
	public void setRealAppDateTime(LocalDateTime realAppDateTime) {
		this.realAppDateTime = realAppDateTime;
	}

	
	

}
