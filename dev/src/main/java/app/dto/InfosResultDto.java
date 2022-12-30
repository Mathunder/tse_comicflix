package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import app.entities.ResultsAPI;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfosResultDto {
	private int limit;
	private int offset;
	private int number_of_page_results;
	private int number_of_total_results;
	private ResultsAPI results;
}
