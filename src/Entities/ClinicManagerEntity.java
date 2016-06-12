package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ClinicManagerEntity implements Serializable{


	private static final long serialVersionUID = 1L;

	private String taskToDo;
	private int clinicManegerId;
	private int ManagerId;
	private Date from;
	private Date to;
	private ArrayList<AppointmentTimeValues> month = new ArrayList<AppointmentTimeValues>();
	private Date day;
	
	public String getTaskToDo() {
		return taskToDo;
	}
	public void setTaskToDo(String taskToDo) {
		this.taskToDo = taskToDo;
	}
	public int getClinicManegerId() {
		return clinicManegerId;
	}
	public void setClinicManegerId(int clinicManegerId) {
		this.clinicManegerId = clinicManegerId;
	}
	public int getManagerId() {
		return ManagerId;
	}
	public void setManagerId(int managerId) {
		ManagerId = managerId;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public ArrayList<AppointmentTimeValues> getMonth() {
		return month;
	}
	public void setMonth(ArrayList<AppointmentTimeValues> month) {
		this.month = month;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
                            

	
}
