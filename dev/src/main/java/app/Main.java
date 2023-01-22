package app;

import app.models.UiModel;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.services.UiController;
import app.ui.frames.MainFrame;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		// Models
		UserModel userModel = new UserModel();
		UiModel uiModel = new UiModel();
		// Controller
		ComicVineService comicVineService = new ComicVineService();
		DatabaseService databaseService = new DatabaseService(userModel);
		UiController uiController = new UiController(uiModel);
		new MainFrame(userModel, uiModel, comicVineService, databaseService, uiController);

	}

}
