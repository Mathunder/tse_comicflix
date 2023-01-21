package app.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UiModel {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private boolean buttonState = true;
	private boolean buttonStateCreateAccount = true;
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	//getters
	public boolean getButtonStateCreateAccount() {
		return this.buttonStateCreateAccount;
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
	
	public void setDisableCreateAccountButton() {
		boolean oldState = buttonStateCreateAccount;
		buttonStateCreateAccount = false;
		this.pcs.firePropertyChange("createAccountButtonStateChange", oldState, buttonStateCreateAccount);
	}
	
	public void setEnableCreateAccountButton() {
		boolean oldState = buttonStateCreateAccount;
		buttonStateCreateAccount = true;
		this.pcs.firePropertyChange("createAccountButtonStateChange", oldState, buttonStateCreateAccount);
	}
}


