package Entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
/**
 * entity class. suppose to have all fields that might be sent to or from server in set appointment
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class Expert implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName;
	private String lastName;
	private String expertise;
	private String workingDays;
	private String idClinic;
	private String clinicName;
	private Time startWorkingHours;
	private Time endWorkingHours;
	private String patID;

	/**
	 * constructor 
	 * return returns  empty expert...
	 */
	public Expert()
	{
		
	}
	/**
	 * constructor that sets inital values
	 * @param expertise inital value
	 * @param firstName initial value
	 * @param lastName initial value
	 * return expert
	 */
	public Expert(String expertise , String firstName ,String lastName)
	{
		this.expertise = expertise;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * constructor that sets inital values
	 * @param id inital value
	 * @param firstname initial value
	 * @param lastname initial value
	 * @param clinicName initial value
	 * @param startWorking initial value
	 * @param endWorking initial value
	 * return expert
	 */
	public Expert(int id, String firstname, String lastname,
			String clinicName, Time startWorking, Time endWorking) {
		this.id=id;
		this.firstName = firstname;
		this.lastName = lastname;
		this.clinicName=clinicName;
		this.startWorkingHours=startWorking;
		this.endWorkingHours=endWorking;
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
	}

	public String getIdClinic() {
		return idClinic;
	}

	public void setIdClinic(String idClinic) {
		this.idClinic = idClinic;
	}

	public Time getStartWorkingHours() {
		return startWorkingHours;
	}

	public void setStartWorkingHours(Time startWorkingHours) {
		this.startWorkingHours = startWorkingHours;
	}

	public Time getEndWorkingHours() {
		return endWorkingHours;
	}

	public void setEndWorkingHours(Time endWorkingHours) {
		this.endWorkingHours = endWorkingHours;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return  firstName+" " + lastName +" "+  expertise ;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getPatID() {
		return patID;
	}
	public void setPatID(String patID) {
		this.patID = patID;
	}

}
