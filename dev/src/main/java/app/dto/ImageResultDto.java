package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageResultDto {
	private String icon_url;
	private String medium_url;
	private String original_url;
	private String screen_url;
	
	public ImageResultDto() {}
	public ImageResultDto(String url) {
		this.medium_url = url;
	}
}


