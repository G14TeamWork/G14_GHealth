package Fixtures;

import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import fit.ActionFixture;
import Controllers.SetAppointmentController;
import Entities.Appointment;
/**
 * This class extends ActionFixture
 * Contains tests for SetAppointment
 * @author elad
 *
 */
public class SetAppointmentTest extends ActionFixture{

	private Appointment app;
	/**
	 * initializer -  connects to database, and sets 219 and 218 to empty meetings
	 */
	public SetAppointmentTest (){
		app = new Appointment();
		GHealthServer.testMain(null);
		MainClass.testMain(null);
		GHealthServer.ServerMasterCont.SACont = new SetAppointmentController();
		GHealthServer.sqlConn.sendSqlUpdate("UPDATE `ghealth`.`appointments` SET `appstatus`='0' WHERE `idappointment`='218'");
		GHealthServer.sqlConn.sendSqlUpdate("UPDATE `ghealth`.`appointments` SET `appstatus`='0' WHERE `idappointment`='219'");
		app.setIdexpert(13);
	}
	
	public void setExpertId(String id){
		app.setExpertid(id);
	}
	public String getExpertId(){
		return app.getExpertid();
	}
	public void setPatientId(String id){
		app.setIdpatient(id);
	}
	
	public String getPatID(){
		return app.getIdpatient();
	}
	
	public void setAppointmentID(String appid){
		app.setIdappointment(appid);
	}
	public String getAppId(){
		return app.getIdappointment();
	}
	
	public boolean SetAppointment(){
		
		return GHealthServer.ServerMasterCont.SACont.SetApp(app);
	}
	
}
