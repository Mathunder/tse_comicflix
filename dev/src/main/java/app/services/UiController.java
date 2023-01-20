package app.services;

import app.models.UiModel;

public class UiController {
	
	private UiModel uiModel;
	public UiController(UiModel uiModel) {
		this.uiModel = uiModel;
	}
	
	public void setEnableLoginButton() {
		uiModel.setEnableLoginButton();
	}
	
	public void setDisableLoginButton() {
		uiModel.setDisableLoginButton();
	}
	
	
	public void setEnableCreateAccountButton() {
		uiModel.setEnableCreateAccountButton();
	}
	
	public void setDisableCreateAccountButton() {
		uiModel.setDisableCreateAccountButton();
	}
	
}
