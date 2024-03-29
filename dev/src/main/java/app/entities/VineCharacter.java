package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.dto.ImageResultDto;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VineCharacter {
	private String aliases;
	private String api_detail_url;
	private int id;
	private String issue_number;
	private String name;
	private ImageResultDto image;
	private String deck;
	private String description;


	public VineCharacter(String aliases, String api_detail_url, int id, String issue_number, String name,
			String image_url, String deck, String description) {
		this.aliases = aliases;
		this.api_detail_url = api_detail_url;
		this.id = id;
		this.issue_number = issue_number;
		this.name = name;
		this.image = new ImageResultDto(image_url);
		this.deck = deck;
		this.description = description;
	}
}
