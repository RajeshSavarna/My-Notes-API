package com.mynotes.api.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Name {
	
	@Schema(description="Salutation of the user", example="Mr.", required = true)
	@NotNull(message="Salutation can not be null")
	@NotBlank(message="Salutation can not be blank")
	private String salutation;
	
	@Schema(description="First name of the user", example="Rahul", required = true)
	@NotNull(message="First Name can not be null")
	@NotBlank(message="First Name can not be blank")
	@JsonProperty("first_name")
	private String firstName;
	
	@Schema(description="Middle name of the user", example="Raj", required = false)
	@JsonProperty("middle_name")
	private String middleName;
	
	@Schema(description="Last name of the user", example="Singh", required = true)
	@NotNull(message="Last Name can not be null")
	@NotBlank(message="Last Name can not be blank")
	@JsonProperty("last_name")
	private String lastName;

}
