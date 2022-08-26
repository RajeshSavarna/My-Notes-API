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

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.model.Notes;
import com.mynotes.api.pojo.NotePOJO;
import com.mynotes.api.service.NotesService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotesController {

	NotesService notesService;

	@Autowired
	public NotesController( NotesService notesService ) {
		this.notesService = notesService;
	}
	
	@Operation(description = "Insert a new note", summary = "Create Note", tags = "Notes")
	@PostMapping(path = "/create-note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public Notes createNote(@RequestBody @Valid NotePOJO note, @RequestHeader("user-id") String userId) throws BusinessException {
		return notesService.createNote(note, userId);
	}
	
	@Operation(description = "Fetch all note of a user", summary = "Get Note", tags = "Notes")
	@GetMapping(path = "/get-note", produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Notes> getNotes(@RequestHeader("user-id") String userId) throws BusinessException {
		return notesService.getNotes(userId);		
	}
	
	@Operation(description = "Delete existing note", summary = "Delete Note", tags = "Notes")
	@DeleteMapping(path = "/delete-note", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Notes deleteNote(@RequestHeader("user-id") String userId, 
			@RequestHeader("note-id") String noteId) throws BusinessException {
		return notesService.deleteNote(userId, noteId);		
	}
	
	@Operation(description = "Update the existing note", summary = "Update Note", tags = "Notes")
	@PutMapping(path = "/update-note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public Notes updateNote(@RequestHeader("user-id") String userId, @RequestHeader("note-id") String noteId, 
			@RequestBody @Valid NotePOJO note) throws BusinessException {
		return notesService.updateNote(userId, noteId, note);		
	}
	
}
