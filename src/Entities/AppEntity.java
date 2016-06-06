package Entities;

import java.util.Calendar;
import java.sql.Time;

public class AppEntity {

	private int idappointment;
	private int idexpert;
	private int idpatient;
	private Calendar appdate;
	private Time start;
	private Time end;
	private String appstatus;
	private String record;
	private float price;
	
	public int getIdappointment() {
		return idappointment;
	}
	public void setIdappointment(int idappointment) {
		this.idappointment = idappointment;
	}
	public int getIdexpert() {
		return idexpert;
	}
	public void setIdexpert(int idexpert) {
		this.idexpert = idexpert;
	}
	public int getIdpatient() {
		return idpatient;
	}
	public void setIdpatient(int idpatient) {
		this.idpatient = idpatient;
	}
	public Calendar getAppdate() {
		return appdate;
	}
	public void setAppdate(Calendar appdate) {
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
	
	
}
