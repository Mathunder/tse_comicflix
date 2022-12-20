package app.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import app.entities.Issue;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDto {
	private int limit;
	private int offset;
	private int number_of_page_results;
	private int number_of_total_results;
	private List<Issue> results;
}
