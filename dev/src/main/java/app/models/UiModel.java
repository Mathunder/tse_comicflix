package app.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UiModel {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private boolean buttonState = true;
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void setDisableLoginButton() {
		boolean oldState = buttonState;
		buttonState = false;
		this.pcs.firePropertyChange("loginButtonStateChange", oldState, buttonState);
	}
	
	public void setEnableLoginButton() {
		boolean oldState = buttonState;
		buttonState = true;
		this.pcs.firePropertyChange("loginButtonStateChange", oldState, buttonState);
	}
}


