package Entities;

import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * entity class. 
 * containing info that needs to be moved between client and server.
 * @author Ruslan
 *
 */
public class Appointment implements Serializable{

	private static final long serialVersionUID = 1L;
	private String idappointment;
	private int idexpert;
	private String idpatient;
	private Date appdate;
	private String AppdateString;
	private Time start;
	private String startS;
	private Time end;
	private String endS;
	private String appstatus;
	private String record;
	private float price;
	private Expert EX;
	
	/**
	 * default constructor
	 * return appointment
	 */
	public Appointment()
	{
		
	}
	/**
	 * constructor. creates appointmnet with given details
	 * @param appdate initial value
	 * @param start  initial value
	 * @param end  initial value
	 * @param expertise  initial value
	 * @param firstname  initial value
	 * @param lastname  initial value
	 * @param idappointment  initial value
	 * @param clinicName  initial value
	 * return returns an appointment
	 */
	public Appointment(Date appdate, Time start, Time end, String expertise, String firstname, String lastname, String idappointment, String clinicName)
	{
		this.start=start;
		this.end=end;
		this.idappointment = idappointment;
		this.appdate = appdate;
		this.EX = new Expert(expertise,firstname,lastname);
		this.EX.setClinicName(clinicName);
	}
	
	public Appointment(Date date) {
		this.appdate=date;
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
		String Date = new SimpleDateFormat("dd.MM.yyyy").format(appdate);
		String HourStart = new SimpleDateFormat("HH:mm").format(start);
		String HourEnd = new SimpleDateFormat("HH:mm").format(end);
		return "AppID["+idappointment+"], " +Date+", "+ HourStart+"-"+HourEnd+ " ," + EX.getFirstName() + " " + EX.getLastName() + ", " + EX.getExpertise() + ", " + EX.getClinicName();
	}
	
	public void setAppdateString(String timeStamp1) {
		this.AppdateString=timeStamp1;
	}
	public String getAppdateString() {
		return AppdateString;
	}
	public String getStartS() {
		return startS;
	}
	public void setStartS(String startS) {
		this.startS = startS;
	}
	public String getEndS() {
		return endS;
	}
	public void setEndS(String endS) {
		this.endS = endS;
	}
}
