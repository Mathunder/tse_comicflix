package app.dto;	

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {
	private int limit;
	private int offset;
	private int number_of_page_results;
	private int number_of_total_results;
	private ResultDto results;
	
	public ResponseDto(){}
	

}


