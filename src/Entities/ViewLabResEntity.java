package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewLabResEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public String date;
	public boolean testResultsFlag=false;
	public ArrayList<Object> arrTest;
	public String PhotoPath="photo-not-available";
	

}
