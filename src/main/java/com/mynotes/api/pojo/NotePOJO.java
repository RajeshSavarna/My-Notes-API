package com.mynotes.api.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NotePOJO {
	
	@Schema(description="Title of the note", example="Movies Watch List", required = true)
	@Size(min = 2, max = 20, message = "Title should be more than 1 character")
	@NotNull(message="Title can not be null")
	private String title;
	
	@Schema(description="Content of the note", example="Brahmastra", required = true)
	@Size(min = 1, message = "Content can not be empty")
	@NotNull(message="Content can not be null")
	private String content;
	
}
