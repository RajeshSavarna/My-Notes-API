package com.mynotes.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"_id",
	"name_details",
	"mobile",
	"email",
	"gender",
	"dob",	
	"notes_list"
})
@Document("userdata")
public class UserData {

	@Id
	@JsonProperty("_id")
	private String id;

	@JsonProperty("name_details")
	private NameDetails nameDetails;

	@JsonProperty("mobile")
	private String mobile;

	@Indexed(unique = true)
	@JsonProperty("email")
	private String email;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("dob")
	private String dob;

	@JsonProperty("notes_list")
	private List<Notes> notesList;
}