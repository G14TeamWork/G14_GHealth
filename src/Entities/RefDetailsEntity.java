package Entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * entity class. suppose to have all fields that might be sent to or from server in reference production / deletion 
 * being sent from and to client/server
 * 
 * @author Ruslan
 *
 */
public class RefDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public String patID;
	public String patName;
	public ArrayList<Reference> rlist;
	
	/**
	 * constructor
	 * @return like any other constructor returns it's own type
	 */
	public RefDetailsEntity(){
		
	}
}
