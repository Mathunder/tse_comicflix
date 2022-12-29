package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import app.entities.InformationsAPI;
import lombok.Data;

/*
 * This class stores the raw result of an API request form an URL (JSON format).
 * The informations requested by the user are stored in the <results> field.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfosResultDto {
	private int limit;
	private int offset;
	private int number_of_page_results;
	private int number_of_total_results;
	private InformationsAPI results;
	
	public InfosResultDto() {}
	
	public InfosResultDto(InformationsAPI res) {
		this.limit = 1;
		this.offset = 0;
		this.number_of_page_results = 1;
		this.number_of_total_results = 1;
		this.results = res;
	}
}
