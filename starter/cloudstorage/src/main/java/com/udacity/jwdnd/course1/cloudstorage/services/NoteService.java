package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
    public int createNote(Note note){
        return noteMapper.insert(note);
    }
    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public List<Note> getNoteByUser(Integer userId) {
        return noteMapper.getNotebyUser(userId);
    }
    public boolean isUserNoteAvailable(Integer userId) {
        return noteMapper.getNotebyUser(userId) == null;
    }
    public  int deleteRecordById(Integer noteId){
        return  noteMapper.deleteNote(noteId);
    }
    public Note getAllNote() {
        return noteMapper.getAllNotes();
    }

    public int updateNote(Note note, Integer noteId) {
        return noteMapper.updateNote(note.getNoteTitle(),note.getNoteDescription(), noteId);
    }

}
