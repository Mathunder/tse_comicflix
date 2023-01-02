package app.entities;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * This class stores the informations about the volume the issue belongs to when searching for an issue.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Volume {
	private String api_detail_url;
	private String name;
	//private int id;
}
