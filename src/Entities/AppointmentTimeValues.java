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
	
	
	public AppointmentTimeValues() {
		this.disSetDateTime = disSetDateTime;
		this.appDateTime = appDateTime;
		this.realAppDateTime = realAppDateTime;
	}
	
								//[ dispatcher	seted DateTime] [ Appointment DateTime		] [ Appointment End Time ] [ Appointment realStart ,realEnd  ]
	public AppointmentTimeValues(Date arrList, Time arrList2, 	Date arrList3,Time arrList4, 	Time arrList5,			 Time arrList6, Time arrList7) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//this.disSetDateTime = LocalDateTime.parse(x1+" "+y1, formatter); //convert (Date and Time) to LocalDateTime ->>> "yyyy-MM-dd HH:mm:ss"
		
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
