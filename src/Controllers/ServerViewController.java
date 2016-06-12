package Controllers;

import views.ServerView;
/**
 * This Class is the controller of server main view. this class was made to make switch panels easier
 * @author Ruslan
 *@param serverView is the servers main window 
 */
public class ServerViewController {

	public ServerView serverView;
	
	/**
	 * This is a constructor of serverviewcontroller
	 * @return a serverviewcontroller - its a constructor
	 */
	public ServerViewController() {
		
		serverView = new ServerView();
		
	}
}