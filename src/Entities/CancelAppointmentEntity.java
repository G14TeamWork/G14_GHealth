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
	
	public CancelAppointmentEntity(String task , String idPatient)
	{
		this.taskToDo = task;
		this.idPatient = idPatient;
		this.appList = new ArrayList<Appointment>();
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
	
	

}
