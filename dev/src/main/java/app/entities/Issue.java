package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

import app.dto.ImageResultDto;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
	private String aliases;
	private String api_detail_url;
	private int id;
	private String issue_number;
	private String name;
	private ImageResultDto image;
	private String deck;
	private String description;
	
	public Issue() {}
	
	public Issue(String aliases, String api_detail_url, int id, String issue_number, String name, String image_url,
			String deck, String description) {
		this.aliases=aliases;
		this.api_detail_url = api_detail_url;
		this.id = id;
		this.issue_number = issue_number;
		this.name = name;
		this.image = new ImageResultDto(image_url);
		this.deck = deck;
		this.description = description;
	}
	public Issue(String aliases, String api_detail_url, int id, String issue_number, String name, String image_url) {
		this.aliases=aliases;
		this.api_detail_url = api_detail_url;
		this.id = id;
		this.issue_number = issue_number;
		this.name = name;
		this.image = new ImageResultDto(image_url);

	}

}
