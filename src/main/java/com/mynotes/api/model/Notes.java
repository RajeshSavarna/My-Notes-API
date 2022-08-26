package com.mynotes.api.model;

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
	"note_id",
	"title",
	"created_date",
	"modified_date",
	"content"
})
public class Notes {

	@JsonProperty("note_id")
	private String noteId;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("created_date")
	private String createdDate;
	
	@JsonProperty("modified_date")
	private String modifiedDate;
	
	@JsonProperty("content")
	private String content;

}