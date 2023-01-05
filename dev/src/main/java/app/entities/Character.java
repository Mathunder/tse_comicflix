package app.entities;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
	private String api_detail_url;
	private int id;
	private String name;
	//image --> ImageResultDto ?
}
