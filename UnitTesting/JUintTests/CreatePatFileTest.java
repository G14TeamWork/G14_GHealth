package JUintTests;

import ocsf.server.GHealthServer;
import Controllers.SetAppointmentController;
import Entities.Patient;
import junit.framework.TestCase;
/**
 * This class is a test class
 * Extends JUnit tests. Checks create patient file  functionality.
 * The checks in this class do access data base in order to create the best simulation possible.
 * @author Elad
 *
 */
public class CreatePatFileTest extends TestCase {
	public Patient patient;
	SetAppointmentController SACont;

	public CreatePatFileTest(){
		patient = new Patient();
		SACont = new SetAppointmentController();
	}
	/**
	 * This method creates a set up for the tests.
	 * Creates connection with data base.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		GHealthServer.testMain(null);
		GHealthServer.sqlConn.sendSqlUpdate(
				"DELETE FROM `ghealth`.`medicalfile` WHERE `idpatient`='1234'");
	}
	/**
	 * This test simulates situation of trying to create new medical file for patient
	 * who already owns a medical file.
	 * Test suppose to fail.
	 * Patient "111" already has a file in data base.
	 */
	public void testFailedExists() {
		boolean result=false;
		patient.setId("111");
		result=SACont.createPatientFile(patient);
		assertFalse(result);
	}
	/**
	 * This test simulates situation of success in creating new medical file.
	 * Test is supposed to be successful.
	 * Patient "1234" doesn't have a medical file in data base.
	 */
	public void testSuccessAdd() {
		boolean result=false;
		patient.setId("1234");
		result=SACont.createPatientFile(patient);
	
		assertTrue(result);
	}
	/**
	 * This test simulates situation where patient id is null.
	 * The test suppose to return incorrect answer.
	 * Obviously there is no patient null.
	 */
	public void testFailNull() {
		boolean result=false;
		patient.setId(null);
		result=SACont.createPatientFile(patient);
	
		assertFalse(result);
	}
	/**
	 * This test simulates situation where patient id entered is too long.
	 * In database there is a limit of 11 digits for patient id. 
	 * Test should succeed.
	 */
	public void testFailTooLong() {
		boolean result=false;
		patient.setId("11111111111111111111111111111111111111111");
		result = SACont.createPatientFile(patient);
		assertFalse(result);
	}
	/**
	 * This test simulates situation of patient id is a non integer input type.
	 * Obviously id doesn't contain letters.
	 * Test should return false.
	 */
	public void testFailInputType() {
		boolean result=false;
		patient.setId("abcd");
		result=SACont.createPatientFile(patient);
	
		assertFalse(result);
	}
}