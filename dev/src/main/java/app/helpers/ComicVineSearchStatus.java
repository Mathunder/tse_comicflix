package app.helpers;

public enum ComicVineSearchStatus {
	FETCHING("fetching"),DONE("done"),IDLE("idle"),ERROR("error");

	private String statusValue;

	ComicVineSearchStatus(String statusValue) {
		this.statusValue=statusValue;
	}

	public String getStatusValue() {
		return statusValue;
	}
}
