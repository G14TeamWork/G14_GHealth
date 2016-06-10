package Entities;

import java.io.Serializable;

public class MedicalFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private String patID;
	private String cardio;
	private String neuro;
	private String genyc;
	private String onco;
	private String patName;
	
	public boolean exists; //flag
	
/**constructors*/
	public MedicalFile(){
		
	}
	public MedicalFile(String patID){
		this.patID=patID;
	}
	
/**getters setters*/	
	public String getPatID() {
		return patID;
	}
	public void setPatID(String patID) {
		this.patID = patID;
	}
	public String getGenyc() {
		return genyc;
	}
	public void setGenyc(String genyc) {
		this.genyc = genyc;
	}
	public String getOnco() {
		return onco;
	}
	public void setOnco(String onco) {
		this.onco = onco;
	}
	public String getCardio() {
		return cardio;
	}
	public void setCardio(String cardio) {
		this.cardio = cardio;
	}
	public String getNeuro() {
		return neuro;
	}
	public void setNeuro(String neuro) {
		this.neuro = neuro;
	}
	public String getPatName() {
		return patName;
	}
	public void setPatName(String patName) {
		this.patName = patName;
	}
}
