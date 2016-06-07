package Entities;

import java.io.Serializable;

public class SetAppointmentEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat = new Patient();
	public Expert exp = new Expert();
	private String task;
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
}