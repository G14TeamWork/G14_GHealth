package Entities;

import java.io.Serializable;

public class RecordAppointmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Appointment appointment;
	public String taskToDo ;
	public String appID="";
	
	public RecordAppointmentEntity( String appid, String tasktodo){
		this.appID = appid;
		this.taskToDo = tasktodo;
		appointment = new Appointment();
	}
	
}
