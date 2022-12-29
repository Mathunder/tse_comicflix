//package app.entities;
//
//import lombok.Data;
//import java.util.List;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
///*
// * This class stores the informations that are fetched from the API.
// * Its fields store informations about an issue or a characters.
// * Thus some fields are specific to the issue type or the character type, some are not specific (ex : <description> field).
// * /!\ You must not mistake the character_credits, which is a list of characters that appear in an issue,
// * and the fact that this class can store informations about a character (in this case, informations are much more detailed).
// */
//@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class InformationsAPI {
//	
//	// Fields specific to the issue type
//	private List<Character> character_credits;
//	private String issue_number;
//	private Volume volume;
//	
//	// Fields that are specific to the character type
//	private String birth;
//	private String count_of_issue_apperances;
//	private List<Creators> creators;
//	// This is a quick summary of the character's life
//	private String deck;
//	private int gender;
//	private Publisher publisher;
//	private String real_name;
//	
//	
//	// Fields that are common to the issue and character type
//	private String description;
//	private int id;
//	private String name;
//}
