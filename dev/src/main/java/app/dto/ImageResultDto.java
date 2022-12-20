package app.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import app.entities.Issue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageResultDto {
	private String icon_url;
	private String medium_url;
	private String original_url;
	private String screen_url;
}
