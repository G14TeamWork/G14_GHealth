package Controllers;

import views.*;

public class MainViewController {

	public mainView MainView;
	
	public MainViewController() {
		MainView = new mainView();
	}
	
	public MainViewController(Boolean debug) {
		MainView = new mainView(debug);
	}
}
