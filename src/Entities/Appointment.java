package Entities;

import java.io.Serializable;
import java.sql.*;

public class Appointment implements Serializable{

	private static final long serialVersionUID = 1L;
	private String idappointment;
	private int idexpert;
	private String idpatient;
	private Timestamp appdate;
	private Time start;
	private Time end;
	private String appstatus;
	private String record;
	private float price;
	private Expert EX;
	
	public Appointment()
	{
		
	}
	public Appointment(Timestamp appdate,String experties,String firstname,String lastname,String idappointment)
	{
		this.idappointment = idappointment;
		this.appdate = appdate;
		this.EX = new Expert(experties,firstname,lastname);
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
	public Timestamp getAppdate() {
		return appdate;
	}
	public void setAppdate(Timestamp appdate) {
		this.appdate = appdate;
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

}
