package com.mynotes.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynotes.api.configuration.JwtUtilsClass;
import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.model.Notes;
import com.mynotes.api.pojo.NotePOJO;
import com.mynotes.api.service.NotesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "my-notes-api")
public class NotesController {

	NotesService notesService;
	JwtUtilsClass jwtUtils;

	@Autowired
	public NotesController( NotesService notesService, JwtUtilsClass jwtUtils ) {
		this.notesService = notesService;
		this.jwtUtils = jwtUtils;
	}
	
	@Operation(description = "Insert a new note", summary = "Create Note", tags = "Notes")
	@PostMapping(path = "/create-note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public Notes createNote(@RequestBody @Valid NotePOJO note, @RequestHeader("Authorization") String authToken) throws BusinessException {
		return notesService.createNote(note, jwtUtils.extractUserId(authToken.substring(7)));
	}
	
	@Operation(description = "Fetch all note of a user", summary = "Get Note", tags = "Notes")
	@GetMapping(path = "/get-note", produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Notes> getNotes(@RequestHeader("Authorization") String authToken) throws BusinessException {
		return notesService.getNotes(jwtUtils.extractUserId(authToken.substring(7)));		
	}
	
	@Operation(description = "Delete existing note", summary = "Delete Note", tags = "Notes")
	@DeleteMapping(path = "/delete-note", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Notes deleteNote(@RequestHeader("Authorization") String authToken, 
			@RequestHeader("note-id") String noteId) throws BusinessException {
		return notesService.deleteNote(jwtUtils.extractUserId(authToken.substring(7)), noteId);		
	}
	
	@Operation(description = "Update the existing note", summary = "Update Note", tags = "Notes")
	@PutMapping(path = "/update-note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public Notes updateNote(@RequestHeader("Authorization") String authToken, @RequestHeader("note-id") String noteId, 
			@RequestBody @Valid NotePOJO note) throws BusinessException {
		return notesService.updateNote(jwtUtils.extractUserId(authToken.substring(7)), noteId, note);		
	}
	
}
