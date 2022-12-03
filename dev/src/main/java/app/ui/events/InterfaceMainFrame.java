package app.ui.events;

import app.entities.User;
import app.ui.frames.MainFrame;

public class InterfaceMainFrame {
	
	private MainFrame MainUi = null;
    
    // default constructor
    public InterfaceMainFrame(MainFrame f) {
        this.MainUi = f;
        
    }

    public void updateLoginInfo(User newUser) {
    	MainUi.setUserProfile(newUser);
    }
}
