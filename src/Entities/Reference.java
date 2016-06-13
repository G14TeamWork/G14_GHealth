package Entities;

import java.io.Serializable;
/**
 * entity class. suppose to have all fields that might be sent to or from server in reference involved actions.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class Reference implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String reftype;
	private String patid;
	private String appid;
	
	/**
	 * constructor 
	 * @return - it's own type
	 */
	public Reference(){
		
	}
	/**
	 * constructor with inital values.
	 * @param patid - inital value 
	 * @param reftype initial value
	 * @param appid initial value
	 * @return like any other constructor - it's own type
	 */
	public Reference(String patid, String reftype, String appid){
		this.patid = patid;
		this.reftype = reftype;
		this.appid = appid;
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReftype() {
		return reftype;
	}
	public void setReftype(String reftype) {
		this.reftype = reftype;
	}
	public String getPatid() {
		return patid;
	}
	public void setPatid(String patid) {
		this.patid = patid;
	}
	
}
