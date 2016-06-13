package Entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * entity class. suppose to have all fields that might be sent to or from server in schedule of expert issue.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class ScheduleEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	public ArrayList<Appointment> alist;
	public int idExp;
	public int noOfApps;// number of appointments
	public String comment;
	public String patName;
	
	/**
	 * constructor that initials array list of appointments
	 * return - like any other constructor - returns it's own type
	 */
	public ScheduleEntity(){
		alist = new ArrayList<Appointment>();
	}
}
