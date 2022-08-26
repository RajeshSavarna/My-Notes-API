package com.mynotes.api.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.logging.LogAround;
import com.mynotes.api.model.Notes;
import com.mynotes.api.model.UserData;
import com.mynotes.api.pojo.NotePOJO;
import com.mynotes.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotesServiceImpl implements NotesService {

	UserRepository userRepo;
	
	private static final String USER_NOT_FOUND= "USER not found";
	private static final String NOTE_NOT_FOUND = "Note not found";

	@Autowired
	public NotesServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * For inserting a new note in the document
	 */
	@LogAround
	@Override
	public Notes createNote(NotePOJO notePojo, String userId) throws BusinessException {
		try {
			Optional<UserData> optionalData = userRepo.findById(userId);
			if (!optionalData.isPresent()) throw new BusinessException("NOTES-2211", USER_NOT_FOUND, 404, null);

			UserData userData = optionalData.get();
			String noteId = UUID.randomUUID().toString().replace("-", "");
			String createdDate = Date.from(Instant.now()).toString();
			Notes notes = new Notes(noteId, notePojo.getTitle(), createdDate, null, notePojo.getContent());
			userData.getNotesList().add(notes);
					
			Notes newlyAddedNote = userRepo.save(userData).getNotesList()
					.stream()
					.filter(note -> noteId.equals(note.getNoteId()))
					.findAny()
					.orElse(null);
			
			log.info("Note added -> user id : {}, note : {}", userId, newlyAddedNote);
			
			return newlyAddedNote;					  
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BusinessException("NOTES-2212", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}

	/**
	 * For fetching all note from the document
	 */
	@LogAround
	@Override
	public List<Notes> getNotes(String userId) throws BusinessException {
		try {
			Optional<UserData> optionalData = userRepo.findById(userId);
			if (!optionalData.isPresent()) throw new BusinessException("NOTES-2221", USER_NOT_FOUND, 404, null);
			return optionalData.get().getNotesList();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BusinessException("NOTES-2222", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}

	/**
	 * For deleting a note the document
	 */
	@LogAround
	@Override
	public Notes deleteNote(String userId, String noteId) throws BusinessException {
		try {
			Optional<UserData> optionalData = userRepo.findById(userId);
			if (!optionalData.isPresent()) throw new BusinessException("NOTES-2231", USER_NOT_FOUND, 404, null);
			
			UserData userData = optionalData.get();
			
			Notes dataToBeRemove = userData.getNotesList().stream()
												.filter(note -> note.getNoteId().equalsIgnoreCase(noteId))
												.findAny()
												.orElse(null);
			
			boolean removed = userData.getNotesList().removeIf(note -> note.getNoteId().equalsIgnoreCase(noteId));
			if (!removed) throw new BusinessException("NOTES-2232", NOTE_NOT_FOUND, 404, null);
			
			userRepo.save(userData);			
			log.info("Note Deleted : {}", dataToBeRemove);
			return dataToBeRemove;
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BusinessException("NOTES-2233", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}

	/**
	 * For updating the existing note in the document
	 */
	@LogAround
	@Override
	public Notes updateNote(String userId, String noteId, NotePOJO notePojo) throws BusinessException {
		try {
			Optional<UserData> optionalData = userRepo.findById(userId);
			if (!optionalData.isPresent()) throw new BusinessException("NOTES-2241", USER_NOT_FOUND, 404, null);
			
			UserData userData = optionalData.get();
			
			int[] matchCount = { 0 };
			userData.getNotesList().forEach(note -> { 
				if  (note.getNoteId().equalsIgnoreCase(noteId)) {
					String modifiedDate = Date.from(Instant.now()).toString();
					note.setTitle(null != notePojo.getTitle() ? notePojo.getTitle() : note.getTitle());
					note.setModifiedDate(modifiedDate);
					note.setContent(null != notePojo.getContent() ? notePojo.getContent() : note.getContent());
					matchCount[0]++;
				}
			});
			
			if (matchCount[0] == 0) throw new BusinessException("NOTES-2242", NOTE_NOT_FOUND, 404, null);
			
			Notes updatedNote = userRepo.save(userData).getNotesList()
					.stream()
					.filter(note -> noteId.equals(note.getNoteId()))
					.findAny()
					.orElse(null);
			
			log.info("Note Updated -> user id : {}, note : {}", userId, updatedNote);
			
			return updatedNote;		
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BusinessException("NOTES-2243", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}

}
