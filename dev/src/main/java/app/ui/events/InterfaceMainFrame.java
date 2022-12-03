package app.ui.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.ui.frames.LoginForm;
import app.ui.frames.MainFrame;

public class InterfaceMainFrame {
	
	private MainFrame MainUi = null;
    
    // default constructor
    public InterfaceMainFrame(MainFrame f) {
        this.MainUi = f;
        
    }

    public void updateLoginInfo(String username) {
    	MainUi.updateUserInfo(username);
    }
}
