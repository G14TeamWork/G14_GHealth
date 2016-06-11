package Entities;

import java.io.Serializable;

public class Reference implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String reftype;
	private String patid;
	private String appid;
	
	public Reference(){
		
	}
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
