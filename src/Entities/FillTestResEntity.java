package Entities;

import java.io.Serializable;

public class FillTestResEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public boolean showbuttonflag;
	public String TestRes="";
	public String TestType="";
}

