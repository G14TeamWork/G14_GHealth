package Entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * entity class. suppose to have all fields that might be sent to or from server in view lab result history.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class ViewLabResEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public String date;
	public boolean testResultsFlag=false;
	public ArrayList<Object> arrTest;
	public String PhotoPath="photo-not-available";
	

}
