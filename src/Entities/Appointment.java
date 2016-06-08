package Entities;

import java.io.Serializable;
import java.sql.*;
import java.util.Date;

public class Appointment implements Serializable{

	private static final long serialVersionUID = 1L;
	private String idappointment;
	private int idexpert;
	private String idpatient;
	private Date appdate;
	private String AppdateString;
	private Time start;
	private Time end;
	private String appstatus;
	private String record;
	private float price;
	private Expert EX;
	
	public Appointment()
	{
		
	}
	public Appointment(Timestamp appdate,String expertise,String firstname,String lastname,String idappointment)
	{
		this.idappointment = idappointment;
		this.appdate = appdate;
		this.EX = new Expert(expertise,firstname,lastname);
	}
	
	public Appointment(int idAppointment, Date date, Time time, Time time2) {
		this.appdate=date;
		this.start=time;
		this.end=time2;
		this.idappointment=String.valueOf(idAppointment);
	}
	public Expert getEX() {
		return EX;
	}
	public void setEX(Expert eX) {
		EX = eX;
	}
	public String getIdappointment() {
		return idappointment;
	}
	public void setIdappointment(String idappointment) {
		this.idappointment = idappointment;
	}
	public int getIdexpert() {
		return idexpert;
	}
	public void setIdexpert(int idexpert) {
		this.idexpert = idexpert;
	}
	public String getIdpatient() {
		return idpatient;
	}
	public void setIdpatient(String appID) {
		this.idpatient = appID;
	}
	public Date getAppdate() {
		return appdate;
	}
	public void setAppdate(Date date) {
		this.appdate = date;
	}
	public Time getStart() {
		return start;
	}
	public void setStart(Time start) {
		this.start = start;
	}
	public Time getEnd() {
		return end;
	}
	public void setEnd(Time end) {
		this.end = end;
	}
	public String getAppstatus() {
		return appstatus;
	}
	public void setAppstatus(String appstatus) {
		this.appstatus = appstatus;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Appointment ["+idappointment+"] " + appdate+" " + EX;
	}
	/**
	 * 
	 */
	public void setAppdateString(String timeStamp1) {
		this.AppdateString=timeStamp1;
	}
	public String getAppdateString() {
		return AppdateString;
	}

}
