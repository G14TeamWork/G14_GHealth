package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewHistoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public boolean showbuttonflag;
	public String date;
	public boolean photoflag=false;
	public String PhotoPath="";
	public boolean testResultsFlag = false;
	public ArrayList<Object> arrTest;
	

}
