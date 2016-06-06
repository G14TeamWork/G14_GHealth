package Entities;

import java.io.Serializable;

public class Expert implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName;
	private String lastName;
	private String experties;
	private String workingDays;
	private String idClinic;
	private String startWorkingHours;
	private String endWorkingHours;
	
	public Expert()
	{
		
	}
	public Expert(String experties , String firstName ,String lastName)
	{
		this.experties = experties;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExperties() {
		return experties;
	}

	public void setExperties(String experties) {
		this.experties = experties;
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

	public String getStartWorkingHours() {
		return startWorkingHours;
	}

	public void setStartWorkingHours(String startWorkingHours) {
		this.startWorkingHours = startWorkingHours;
	}

	public String getEndWorkingHours() {
		return endWorkingHours;
	}

	public void setEndWorkingHours(String endWorkingHours) {
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
		return  firstName+" " + lastName +" "+  experties ;
	}

}
