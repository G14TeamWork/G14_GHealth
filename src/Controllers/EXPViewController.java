package Controllers;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import ocsf.server.GHealthServer;
import mainPackage.MainClass;
import views.ExpView;
import views.LoginView;
import Controllers.IRefresh;
import Entities.Appointment;
import Entities.RecordAppointmentEntity;
import Entities.ScheduleEntity;

public class EXPViewController implements Observer,IRefresh, Serializable {
	
	private static final long serialVersionUID = 1L;
	public ExpView expview;
	public RecordAppointmentEntity RAE1 = new RecordAppointmentEntity();
	public ScheduleEntity se;
	public String tmp;
	public EXPViewController() {
		expview = new ExpView();
	}
	
	public void checkApp(String appID1, String expertID){
		RAE1.appID=appID1;
		RAE1.expID=expertID;
		RAE1.taskToDo="search";
		MainClass.ghealth.sendMessegeToServer(RAE1);
	}
	public void checkAppSQL(RecordAppointmentEntity rae) {
		ArrayList<Object> arrList = new ArrayList<Object>();
		String query = "SELECT * FROM ghealth.appointments WHERE idexpert='"+ rae.expID +"' AND idappointment ='" + rae.appID+"'" ;
			arrList=GHealthServer.sqlConn.sendSqlQuery(query);
		if (!arrList.isEmpty()){
			rae.appointment.setIdpatient(String.valueOf((int)arrList.get(2)));
			rae.appointment.setRecord((String)arrList.get(11));
		}
		else
			rae.appointment.setIdpatient(null);
	}
	
	public void showExpSched(){
		se = new ScheduleEntity();
		String str = "";
		String tmp = "";
		se.idExp = Integer.valueOf(MainClass.masterControler.LoginCont.loginEntity.getUsername());
		se.comment = "";
		MainClass.ghealth.sendMessegeToServer(se);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!se.comment.equals("xxx")){
			for ( int i = 0 ; i < se.alist.size() ; i++ ){
				if((se.alist.get(i).getAppstatus().equals("1")||(MainClass.masterControler.EXPVCont.expview.checkboxAll.isSelected()))){
					if (se.alist.get(i).getAppstatus().equals("2")){
						tmp = "<font color=\"white\">__</font><b><font color=\"green\">finished</font></b>";
					}else if (se.alist.get(i).getAppstatus().equals("1")){
						tmp = "";
					}else tmp = "<font color=\"white\">__</font><b><font color=\"red\">didn't apear!</font></b> ";
					str = str + "* <b>Time: </b>"+  se.alist.get(i).getStartS().substring(0,5)+
							"<font color=\"white\">__</font><b>App No.: </b> "
							+ se.alist.get(i).getIdappointment() + "<font color=\"white\">__</font><b>Patient: </b> "
							+ se.alist.get(i).getEndS() +tmp+"<br>";	
				}
			}
		}else{
			str = "No appointments were set for today.";
		}
		expview.sched.setText(str);
	}

	public void serverShowSchedule(ScheduleEntity SE){
		ArrayList<Object> arrList = new ArrayList<Object>();
		int index=0;
		String query = "SELECT `idappointment`, `idpatient`, `start`, `appstatus` FROM ghealth.appointments WHERE `idexpert`='"
				+SE.idExp+"' AND `appstatus`!='0' AND `appdate`='"+new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())
				+"' ORDER BY `start`" ;
		arrList=GHealthServer.sqlConn.sendSqlQuery(query);
				if (arrList.isEmpty()){
			SE.comment = "xxx";
		}else{
			while(!arrList.isEmpty()){
				SE.alist.add(new Appointment());
				SE.alist.get(index).setIdappointment(String.valueOf((int)arrList.get(0)));
				arrList.remove(0);
				SE.alist.get(index).setIdpatient(String.valueOf((int)arrList.get(0)));
				arrList.remove(0);
				SE.alist.get(index).setStartS(((Time)arrList.get(0)).toString());
				arrList.remove(0);
				SE.alist.get(index).setAppstatus((String)arrList.get(0));
				arrList.remove(0);
				index++;
			}
			SE.noOfApps = index;
			for ( int i = 0 ; i < index ; i ++ ){
				query = "SELECT `firstname`, `lastname` FROM ghealth.patient WHERE `id`='"+SE.alist.get(i).getIdpatient()+"'";
				System.out.println("Second query : "+ query);
				arrList=GHealthServer.sqlConn.sendSqlQuery(query);
				SE.alist.get(i).setEndS((String)arrList.get(0)+" "+(String)arrList.get(1));
			}
		}
	}
	
	@Override
	public void refreshView() {
		
	}

	@Override
	public void update(Observable o, Object arg) {
			if(arg instanceof RecordAppointmentEntity){
				if(((RecordAppointmentEntity)arg).taskToDo.equals("search"))
				{
					if(((RecordAppointmentEntity)arg).appointment.getIdpatient()!=null && !(((RecordAppointmentEntity)arg).appointment.getIdpatient().equals("0"))){
						MainClass.masterControler.EXPVCont.RAE1.appointment.setIdpatient(((RecordAppointmentEntity)arg).appointment.getIdpatient());
						MainClass.masterControler.EXPVCont.RAE1.appointment.setRecord(((RecordAppointmentEntity)arg).appointment.getRecord());
						MainClass.masterControler.EXPVCont.expview.sched.setText("");
						MainClass.masterControler.EXPVCont.expview.checkboxAll.setSelected(false);
						MainClass.masterControler.RACont.RecordAppointview.idPatientLabel.setText("Patient : " + MainClass.masterControler.EXPVCont.RAE1.appointment.getIdpatient());
						MainClass.masterControler.RACont.RecordAppointview.appNoLabel.setText("Appointment : " + ((RecordAppointmentEntity)arg).appID);
						tmp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
						MainClass.masterControler.RACont.RecordAppointview.newRefs = false;
						MainClass.masterControler.RACont.RecordAppointview.startHourLabel.setText("Start Time : " + tmp);
						MainClass.masterControler.EXPVCont.RAE1.appointment.setStartS(tmp);
						MainClass.masterControler.RACont.RecordAppointview.btnProduceLabReference.setVisible(true);
						MainClass.masterControler.RACont.RecordAppointview.record.setText(MainClass.masterControler.EXPVCont.RAE1.appointment.getRecord());
						if (MainClass.masterControler.RACont.RecordAppointview.record.getText().equals("0"))
							MainClass.masterControler.RACont.RecordAppointview.record.setText("");
						MainClass.masterControler.setView(MainClass.masterControler.RACont.RecordAppointview,MainClass.masterControler.RACont);
					}
					else if (!((RecordAppointmentEntity)arg).appID.equals(null))
						JOptionPane.showMessageDialog(null, "No matching appointment for you in data server!");
				}
			}else{
				if(arg instanceof ScheduleEntity){
					se.alist = ((ScheduleEntity)arg).alist;
					se.noOfApps = ((ScheduleEntity)arg).noOfApps;
					se.comment = ((ScheduleEntity)arg).comment;
				}
			}
	}
}