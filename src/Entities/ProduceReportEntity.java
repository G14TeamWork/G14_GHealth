package Entities;

import java.io.Serializable;

public class ProduceReportEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String taskToDo;
	private int idClinic;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskToDo() {
		return taskToDo;
	}

	public void setTaskToDo(String taskToDo) {
		this.taskToDo = taskToDo;
	}

	public int getIdClinic() {
		return idClinic;
	}

	public void setIdClinic(int idClinic) {
		this.idClinic = idClinic;
	}

	
}
