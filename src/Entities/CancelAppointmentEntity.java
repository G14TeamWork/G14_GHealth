
package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class CancelAppointmentEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Appointment> appList;
	private String taskToDo;
	private String idPatient;
	private String idapp;
	
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
	

}
