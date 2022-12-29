package app.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import app.dto.ImageResultDto;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsAPI {
	
	// Fields specific to the issue type
	private List<Character> character_credits;
	private String issue_number;
	private Volume volume;
	
	// Fields that are specific to the character type
	private String birth;
	private String count_of_issue_apperances;
	private List<Creators> creators;
	// This is a quick summary of the character's life
	private String deck;
	private int gender;
	private Publisher publisher;
	private String real_name;
	
	// Fields that are common to the issue and character type
	private String description;
	private int id;
	private String name;
	private String aliases;
	private String api_detail_url;
	private ImageResultDto image;
	
	
	public ResultsAPI() {}
	
	public ResultsAPI(String aliases, String api_detail_url, int id, String issue_number, String name, String image_url) {
		this.aliases=aliases;
		this.api_detail_url = api_detail_url;
		this.id = id;
		this.issue_number = issue_number;
		this.name = name;
		this.image = new ImageResultDto(image_url);
	}

}
