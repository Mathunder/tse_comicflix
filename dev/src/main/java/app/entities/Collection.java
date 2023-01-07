package app.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {
	private String name;
	private List<Issue> issues;
	
	
	public Collection(String cName, List<Issue> iss){
		this.name = cName;
		this.issues = iss;
	}
	
	public void  addIssuesInCollection(Issue iss) {
		issues.add(iss);
	}
	
	public void removeIssuesInCollection(Issue iss) {
		issues.removeIf(n -> n.getId() == iss.getId());
	}
}


