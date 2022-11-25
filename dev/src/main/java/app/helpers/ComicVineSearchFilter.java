package app.helpers;

public enum ComicVineSearchFilter {
	ISSUE("issue"),CHARACTER("character");

	private String filterValue;

	ComicVineSearchFilter(String filterValue) {
		this.filterValue=filterValue;
	}

	

	public String getFilterValue() {
		return filterValue;
	}
}
