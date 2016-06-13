package Entities;
/**
 * entity class. suppose to have all fields that might be sent to or from server in any reports action.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class Report {
	
	private int idClinic;
	private int day;
	private int numberOfPatient;
	private long Max;
	private long Min;
	private long Avg;
	private long Sd;
	
	public Report()
	{
		
	}
	
	public Report(int idClinic, int day, int numberOfPatient, long max,long min, long avg, long sd)
	{
		this.idClinic = idClinic;
		this.day = day;
		this.numberOfPatient = numberOfPatient;
		this.Max = max;
		this.Min = min;
		this.Avg = avg;
		this.Sd = sd;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getNumberOfPatient() {
		return numberOfPatient;
	}
	public void setNumberOfPatient(int numberOfPatient) {
		this.numberOfPatient = numberOfPatient;
	}
	public long getMax() {
		return Max;
	}
	public void setMax(long max) {
		Max = max;
	}
	public long getMin() {
		return Min;
	}
	public void setMin(long min) {
		Min = min;
	}
	public long getAvg() {
		return Avg;
	}
	public void setAvg(long avg) {
		Avg = avg;
	}
	public long getSd() {
		return Sd;
	}
	public void setSd(long sd) {
		Sd = sd;
	}
	public int getIdClinic() {
		return idClinic;
	}
	public void setIdClinic(int idClinic) {
		this.idClinic = idClinic;
	}
	
	

}
