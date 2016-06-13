package Entities;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AppointmentTimeValues {


	private long fromDisToAppDateDiffInMinutes;
	private long fromAppDateToRealAppDateDiffInMinutes;
	
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
		LocalDateTime disSetDateTime;
		LocalDateTime appDateTime;
		LocalDateTime realAppDateTime;
		
		disSetDateTime = LocalDateTime.parse(setedDate+" "+setedTime, formatter); //convert (Date and Time) to LocalDateTime ->>> "yyyy-MM-dd HH:mm:ss"
		appDateTime = LocalDateTime.parse(appDate+" "+appStartTime, formatter);
		realAppDateTime = LocalDateTime.parse(appDate+" "+realStartTime, formatter);
		this.fromDisToAppDateDiffInMinutes = java.time.Duration.between(appDateTime, disSetDateTime).toMinutes();
		this.fromAppDateToRealAppDateDiffInMinutes = java.time.Duration.between(realAppDateTime, appDateTime).toMinutes();
	
	
	}

	public long getFromDisToAppDateDiffInMinutes() {
		return fromDisToAppDateDiffInMinutes;
	}

	public void setFromDisToAppDateDiffInMinutes(long fromDisToAppDateDiffInMinutes) {
		this.fromDisToAppDateDiffInMinutes = fromDisToAppDateDiffInMinutes;
	}

	public long getFromAppDateToRealAppDateDiffInMinutes() {
		return fromAppDateToRealAppDateDiffInMinutes;
	}

	public void setFromAppDateToRealAppDateDiffInMinutes(
			long fromAppDateToRealAppDateDiffInMinutes) {
		this.fromAppDateToRealAppDateDiffInMinutes = fromAppDateToRealAppDateDiffInMinutes;
	}
	

	
	

}
