package Entities;

import java.io.File;
import java.io.Serializable;

import mainPackage.MainClass;

public class FillTestResEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public boolean showbuttonflag;
	public String TestRes="";
	public String TestType="";
	public String PhotoPath="photo-not-available";
	//public File PhotoFile;
	public boolean updateFlag = false;
	//public boolean testResultsFlag = false;
	public String labworkerFirstName=MainClass.masterControler.LoginCont.loginEntity.getFirstname();
	public String labworkerLastName=MainClass.masterControler.LoginCont.loginEntity.getLastname();
}

