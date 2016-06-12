package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class RefDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public String patID;
	public String patName;
	public ArrayList<Reference> rlist;
	
	public RefDetailsEntity(){
		
	}
}
