package app.entities;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * This class stores the informations about the publisher when searching for an issue.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Publisher {
	private String api_detail_url;
	private int id;
	private String name;
}
