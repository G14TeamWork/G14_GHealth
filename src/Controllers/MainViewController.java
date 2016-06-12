package Controllers;

import views.*;
/**
 * This class is made in order to simplify switching pannels.
 * It is a controller that contains view that is the panel of first window of client
 * @author Ruslan
 * @param Mainview - the first panel of client.
 */
public class MainViewController {

	public mainView MainView;
	
	/**
	 * this is mainviewcontroller constructor.
	 * initializes in it MainView
	 * @return this is a constructor therefore return same type..
	 */
	public MainViewController() {
		MainView = new mainView();
	}
	
	/**
	 * this method is in charge of entering debug mode.
	 * make programmer's life a little easier
	 * @param debug if debug is true system enters debug mode (prints alot of logs), else it doesn't.
	 */
	public MainViewController(Boolean debug) {
		MainView = new mainView(debug);
	}
}
