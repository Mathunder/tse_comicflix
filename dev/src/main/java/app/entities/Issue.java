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
	//private String publisher;
	private String description;
//	private List<Creators> person_credits;
//	private List<Character> character_credits;
}
