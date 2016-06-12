package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewAppHistoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	//public boolean appFlag=false;
	//public String date;
	public boolean appResultsFlag=false;
	public ArrayList<Object> arrApp;

}