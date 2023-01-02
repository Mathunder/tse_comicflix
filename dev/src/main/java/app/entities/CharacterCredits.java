package app.entities;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * This class stores the informations about the character_credits when searching an issue.
 * /!\ It is different from a search of a character.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterCredits {
	private String api_detail_url;
	private int id;
	private String name;
}
