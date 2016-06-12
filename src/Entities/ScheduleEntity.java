package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ScheduleEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	public ArrayList<Appointment> alist;
	public int idExp;
	public int noOfApps;// number of appointments
	public String comment;
	public String patName;
	
	public ScheduleEntity(){
		alist = new ArrayList<Appointment>();
	}
}
