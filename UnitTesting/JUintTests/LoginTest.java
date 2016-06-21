package JUintTests;

import ocsf.server.GHealthServer;
import Controllers.LoginController;
import Entities.LoginEntity;
import junit.framework.TestCase;
/**
 * This class is a test class
 * Extends JUnit tests. Checks LOGIN functionality.
 * The checks in this class do access data base in order to create the best simulation possible.
 * @author Elad
 */
public class LoginTest extends TestCase {
	LoginController loginCONT = new LoginController();
	public LoginEntity le = new LoginEntity();
	
	/**
	 * This method creates a set up for the tests.
	 * Creates connection with data base.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		GHealthServer.testMain(null);
		GHealthServer.sqlConn.sendSqlUpdate(
				"UPDATE `ghealth`.`users` SET `status`='0',`loginatmps`='0' WHERE `username`='11'");
		GHealthServer.sqlConn.sendSqlUpdate(
				"UPDATE `ghealth`.`users` SET `status`='1' WHERE `username`='21'");
		GHealthServer.sqlConn.sendSqlUpdate(
				"UPDATE `ghealth`.`users` SET `loginatmps`='3' WHERE `username`='13'");
	}
/**
 * This test simulates successful login.
 * Gets real name and real password, and tries to log in.
 */
	public void testSuccessLogin() {
		boolean result=false;
		le.setUsername("11");
		le.setPassword("gal");
		result = loginCONT.toSQL(le);
	
		assertTrue(result);
	}
	/**
	 * This test simulates situation where logged in user tries to log in.
	 * Test suppose to return false answer because we defined in data base that
	 * this specific user is already logged in.
	 */
	public void testFailAlreadyLoggedIn() {
		boolean result=false;
		le.setUsername("21");
		le.setPassword("fish");
		result = loginCONT.toSQL(le);
	
		assertFalse(result);
	}
	/**
	 * This test simulates situation where username doesn't exists.
	 * Test suppose to return false. 
	 * The user name 888 doesn't exist in data base.
	 */
	public void testFailWrongUsername() {
		boolean result=false;
		le.setUsername("888");
		le.setPassword("fish");
		result = loginCONT.toSQL(le);
	
		assertFalse(result);
	}
	/**
	 * This test simulates situation of correct user name but incorrect password.
	 * Test suppose to return false.
	 * The correct password of 11 is gal.
	 */
	public void testFailIncorrectPw() {
		boolean result=false;
		le.setUsername("11");
		le.setPassword("guli");
		result = loginCONT.toSQL(le);
	
		assertFalse(result);
	}
	/**
	 * This test simulates situation where blocked user tries to log in.
	 * Blocked user is a user with at least 3 log in attempts.
	 * Of course user name and password are correct but in the data base we've
	 * defined log in attempts for user "13" to 3.
	 * Therefore test should return false.
	 */
	public void testFailTooManyAttempts() {
		boolean result=false;
		le.setUsername("13");
		le.setPassword("exp");
		result = loginCONT.toSQL(le);
	
		assertFalse(result);
	}
}