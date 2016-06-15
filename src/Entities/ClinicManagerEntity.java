package Entities;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ocsf.server.GHealthServer;
/**
 * entity class. suppose to have all fields that might be sent to or from server in reports actions.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class ClinicManagerEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String taskToDo;
	private int clinicId;
	private int ManagerId;
	private Date from;
	private Date to;
	private WeekReport week = new WeekReport();
	private MonthReport month = new MonthReport();
	private DayReport day = new DayReport();
	
	public void createWeeklyReport(Date date)
	{
		
	}
	
	public String getTaskToDo() {
		return taskToDo;
	}
	public void setTaskToDo(String taskToDo) {
		this.taskToDo = taskToDo;
	}
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicManegerId) {
		this.clinicId = clinicManegerId;
	}
	public int getManagerId() {
		return ManagerId;
	}
	public void setManagerId(int managerId) {
		ManagerId = managerId;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	
	public String generateDayDateToSql(Date date)
	{
		
		return new SimpleDateFormat("yyyyMMdd").format(this.to);
	}
	public String generateMonthDateToSql(Date date)
	{
		String Mdate= new SimpleDateFormat("yyyyMMdd").format(this.to);
		return Mdate.substring(0,5)+"__";
	}
	public String getTo_Sql()
	{
		return new SimpleDateFormat("yyyyMMdd").format(this.to);	 //format for update mySql workbench
	}

	public String getFrom_Sql()
	{
		return new SimpleDateFormat("yyyyMMdd").format(this.from); 	
	}

	public WeekReport getWeek() {
		return week;
	}

	public void setWeek(WeekReport week) {
		this.week = week;
	}

	public MonthReport getMonth() {
		return month;
	}

	public void setMonth(MonthReport month) {
		this.month = month;
	}

	public DayReport getDay() {
		return day;
	}

	public void setDay(DayReport day) {
		this.day = day;
	}
	
}