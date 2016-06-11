package Entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

public class SetAppointmentEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat = new Patient();
	public Expert exp = new Expert();
	public Appointment app = new Appointment();
	private String task;
	public ArrayList<Expert> ExpList=new ArrayList<Expert>();
	public ArrayList<Appointment> AppList=new ArrayList<Appointment>();
	public ArrayList<Time> HourList=new ArrayList<Time>();
	public ArrayList<Integer> AppIDlist=new ArrayList<Integer>();
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
}