package com.mynotes.api.pojo;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class UserPOJO {

	//private String id;
	
	@Valid
	@NotNull(message="Name Details can not be null")
	@JsonProperty("name_details")
    private Name nameDetails;
	
	@Schema(description="Mobile of the user", example="8765876565", required = true)
	@Size(min = 10, max = 10, message = "Mobile Number should be exact 10 characters.")
    private String mobile;
	
	@Schema(description="Email of the user", example="username@corp.com", required = true)
	@NotNull(message="Email id can not be null")
	@NotBlank(message="Email id can not be blank")
	@Email(message = "Provided email is not in valid format. Valid Format Ex. username@corp.com", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
	
	@Schema(description="Password of the user", example="76gyg%78*@&gu", required = true)
	@NotNull(message="Password can not be null")
	@Size(min = 6, message = "Password should be atleast 6 characters.")
	private String password;
	
	@Schema(description="Gender", example="Male/Female", required = false)
    private String gender;
	
	@Schema(description="Date of Birth", example="09-03-1990", required = false)
    private String dob;

}