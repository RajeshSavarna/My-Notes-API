package com.mynotes.api.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.model.Notes;
import com.mynotes.api.pojo.NotePOJO;

@Service
public interface NotesService {
	
	public Notes createNote(NotePOJO note, String userId) throws BusinessException;
	
	public List<Notes> getNotes(String userId) throws BusinessException;

	public Notes deleteNote(String userId, String noteId) throws BusinessException;

	public Notes updateNote(String userId, String noteId, @Valid NotePOJO note) throws BusinessException;
}
