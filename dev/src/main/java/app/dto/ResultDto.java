package app.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.entities.*;
import lombok.Data;

/*
 * This class stores the informations about an issue or a character. 
 * Whether the search is about an issue or a character, some fields (that are specific to each type) will be null,
 * and other (that are common to both type) will be assigned values.
 * 
 * /!\ List<CharacterCredits> doesn't retrieve the informations in the JSON format the API returns.
 * The format is as described here :
 * 
 * "character_credits": [
 * 		{
 * 			"api_detail_url": ...
 * 			"id": ...
 * 			"name": ...
 * 			"site_detail_url": ...
 * 		}
 * 		{
 * 			...
 *		}
 *	]
 *
 * One must use ArrayList<CharacterCredits> instead.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDto {
	// Fields specific to the issue type
	private ArrayList<CharacterCredits> character_credits;
	private String issue_number;
	private Volume volume;
	private ArrayList<PersonCredits> person_credits;

	// Fields that are specific to the character type
	private String birth;
	private String count_of_issue_apperances;
	private ArrayList<Creators> creators;
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
	private String cover_date;
	private String resource_type;
	
	// Fields specific to the Volume type
	private int count_of_issues;
	private ArrayList<Issue> issues;

	public Issue convertToIssue() {
		return new Issue(aliases, api_detail_url, id, issue_number, name, this.image.getMedium_url(), deck,
				description, volume);
	}

	public VineCharacter convertToCharacter() {
		return new VineCharacter(aliases, api_detail_url, id, issue_number, name, this.image.getMedium_url(), deck,
				description);
	}
	
	public Issue getSpecificIssue(int issue_number) {
		Issue issue = new Issue();
		for (int i = 0; i < this.issues.size(); i++) {
			if (Integer.parseInt(this.issues.get(i).getIssue_number()) == issue_number) {
				issue = this.issues.get(i);
			}
		}
		return issue;
	}
}
