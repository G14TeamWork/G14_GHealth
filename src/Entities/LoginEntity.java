package Entities;

import java.io.Serializable;
import java.net.URL;
import java.sql.Time;

import mainPackage.MainClass;

/**
 * entity class. suppose to have all fields that might be sent to or from server in login.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class LoginEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Boolean logout = false;
	private Boolean connectionLost = false;
	
	private String username;
	private String password;
	private String usertype;
	private String firstname;
	private String lastname;
	private String email;
	private int status;
	private boolean ArrayListReturnedEmpty;
	private int loginAttempts;

	private boolean loginOK = false;

	
	public LoginEntity(String usr,String pass){
		username = usr;
		password = pass;
	}

	
	public LoginEntity() {
		// TODO Auto-generated constructor stub
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public boolean isLoginOK() {
		return loginOK;
	}
	public void setLoginOK(boolean loginOK) {
		this.loginOK = loginOK;
	}

	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int i) {
		this.status = i;
	}




	@Override
	public String toString() {
		return username;
	}

	public int getloginAttempts() {
		return loginAttempts;
	}


	public void setloginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public boolean getArrayListReturnedEmpty() {
		return ArrayListReturnedEmpty;
	}



	public void setArrayListReturnedEmpty(boolean bool) {
		ArrayListReturnedEmpty = bool;
	}
	public Boolean isLogout() {
		return logout;
	}


	public void setLogout(Boolean logout) {
		this.logout = logout;
	}


	public Boolean isConnectionLost() {
		return connectionLost;
	}


	public void setConnectionLost(Boolean connectionLost) {
		this.connectionLost = connectionLost;
	}
}
