package Entities;

import java.io.File;
import java.io.Serializable;

import javax.swing.Icon;

public class ViewHistoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public boolean showbuttonflag;
	public String date;
	public boolean photoflag=false;
	public File photo;
	

}
