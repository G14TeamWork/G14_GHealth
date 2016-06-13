package Entities;

import java.io.Serializable;
/**
 * entity class. suppose to have all fields that might be sent to or from server in record appointment.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class RecordAppointmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Appointment appointment;
	public String taskToDo ;
	public String appID="";
	public String expID;
	
	/**
	 * constructor. creates new appointment in it.
	 * return like any other constructor, returns its' type
	 */
	public RecordAppointmentEntity(){

		appointment = new Appointment();
	}
	
}
