package Fixtures;
import mainPackage.MainClass;
import ocsf.server.GHealthServer;
import Controllers.FillTestResController;
import Entities.FillTestResEntity;
import fit.ActionFixture;

public class InsertTestResultsTest extends ActionFixture {
	Boolean result = true;
	FillTestResEntity Entity = new FillTestResEntity();
	String testType;
	/**
	 * id = 444
	 * id ref = 10
	 * type = rentgen - hand
	 */
	
	
	public InsertTestResultsTest()
	{
		
		MainClass.testMain(null);
		GHealthServer.testMain(null);
		GHealthServer.ServerMasterCont.FTRCont = new FillTestResController();
	}
	
	public void setPatientId(String id)
	{
		Entity.pat.setId(id);
	}	
/*
	public void setTestType(String testType)
	{
		Entity.TestType = testType;
	}
*/
	public void setTestResText(String TestResText)
	{
		Entity.TestRes = TestResText;
	}
/*	
	public void setRefId(String idRef)
	{
		Entity.arrRef.add(idRef);
		Entity.testIndex = 0;
	}*/
	public boolean checkIdPat()
	{
		return GHealthServer.ServerMasterCont.FTRCont.checkExistanceSql(Entity);
	}
	
	public boolean checkPatRef()
	{
		return GHealthServer.ServerMasterCont.FTRCont.checkExistanceReferenceSql(Entity);
	}
	public void setTestType(String testType)
	{
		this.testType = testType;
	}
	public boolean checkTestType()
	{
		if(Entity.arrRef.contains(testType))
		{
		Entity.TestType = testType;
		return true;
		}
		return false;
	}
	public boolean checkInsertTestRes()
	{
		return GHealthServer.ServerMasterCont.FTRCont.insertTestResSql(Entity);
	}
	
}





/*	public void setLabWorkerFirstName(String firstName)
{
	Entity.labworkerFirstName = firstName;
}
public void setLabWorkerLastName(String lastName)
{
	Entity.labworkerLastName = lastName;
}
public boolean checkRefMatch()
	{
		return type.equals(Entity.arrRef.get(1));
	}*/