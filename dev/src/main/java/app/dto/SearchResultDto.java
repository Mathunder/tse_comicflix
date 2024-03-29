package app.dto;	

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDto {
	private int limit;
	private int offset;
	private int number_of_page_results;
	private int number_of_total_results;
	private List<ResultDto> results;
	
	public SearchResultDto() {}
	
	public SearchResultDto(List<ResultDto> issues) {
		this.limit = 16;
		this.offset=0;
		this.number_of_page_results = (int) Math.ceil((double) issues.size() / 16);
		this.number_of_total_results = issues.size();
		this.results = issues;
	}

}


