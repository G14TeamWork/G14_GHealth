package Entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * entity class. suppose to have all fields that might be sent to or from server in general view history.
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class ViewHistoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Patient pat= new Patient();
	public boolean showbuttonflag;
	public String date;
	public boolean photoflag=false;


}
