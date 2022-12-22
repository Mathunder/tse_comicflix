package app;

import app.entities.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.ui.frames.MainFrame;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		//Models
		UserModel userModel = new UserModel();
		
		//Controller
		ComicVineService comicVineService = new ComicVineService();
		DatabaseService databaseService = new DatabaseService(userModel);
		
		new MainFrame(userModel, comicVineService, databaseService);

	}

}
