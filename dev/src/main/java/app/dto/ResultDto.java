package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.entities.Issue;
import app.entities.VineCharacter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDto {
	private String aliases;
	private String api_detail_url;
	private int id;
	private String issue_number;
	private String name;
	private ImageResultDto image;
	// this prop will determine if the result is an issue or a character
	private String resource_type;
	private String deck;
	private String description;
	
	public Issue convertToIssue() {
		return new Issue(aliases,  api_detail_url,  id,  issue_number,  name,  this.image.getMedium_url(), deck, description);
	}
	
	public VineCharacter convertToCharacter() {
		return new VineCharacter(aliases,  api_detail_url,  id,  issue_number,  name,  this.image.getMedium_url(), deck, description);
	}
}
