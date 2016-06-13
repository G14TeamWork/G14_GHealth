
package Entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * entity class. suppose to have all fields that might be sent to or from server in cancel appointment.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class CancelAppointmentEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Appointment> appList;
	private String taskToDo;
	private String idPatient;
	private String idapp;
	private String firstName;
	private String lastName;
	
	/**
	 * constructor - initals values of task and id
	 * @return returns cancel appointment entity
	 * @param task initial value
	 * @param id initial value
	 */
	public CancelAppointmentEntity(String task , String id)
	{
		if(task.equals("search"))
		{
			this.taskToDo = task;
			this.idPatient = id;
			this.appList = new ArrayList<Appointment>();
		}
		else if(task.equals("delete"))
		{
			this.taskToDo = task;
			this.idapp = id;
		}
	}

	public ArrayList<Appointment> getAppList() {
		return appList;
	}

	public void setAppList(ArrayList<Appointment> appList) {
		this.appList = appList;
	}

	public String getTaskToDo() {
		return taskToDo;
	}

	public void setTaskToDo(String taskToDo) {
		this.taskToDo = taskToDo;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public String getIdapp() {
		return idapp;
	}

	public void setIdapp(String idapp) {
		this.idapp = idapp;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



}
