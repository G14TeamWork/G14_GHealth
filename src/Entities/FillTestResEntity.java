package Entities;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import mainPackage.MainClass;

/**
 * entity class. suppose to have all fields that might be sent to or from server in fill test results.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class FillTestResEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public String taskToDo="";
	public String TestRes="";
	public String TestType="";
	public String PhotoPath="photo-not-available";
	public String labworkerFirstName=MainClass.masterControler.LoginCont.loginEntity.getFirstname();
	public String labworkerLastName=MainClass.masterControler.LoginCont.loginEntity.getLastname();
	public ArrayList<Object> arrRef = new ArrayList<>();
	public ArrayList<Object> arrRefid = new ArrayList<>();
	public int testIndex=-1;
}

