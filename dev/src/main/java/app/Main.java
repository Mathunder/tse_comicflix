package app;

import app.services.ComicVineService;
import app.ui.frames.MainFrame;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ComicVineService comicVineService = new ComicVineService();
		new MainFrame(comicVineService);



	}

}
