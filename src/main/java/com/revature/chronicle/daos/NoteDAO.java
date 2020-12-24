package com.revature.chronicle.daos;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;

import java.util.List;

/**
 * Data access object interface for note data
 */
public interface NoteDAO {
    Note getNote(int id);
    List<Note> getNotes();
    List<Note> getNotes(Tag tag);

    boolean addNote(Note note);
    boolean updateNote(Note note);
    boolean deleteNote(Note note);
    boolean addTag(Note note, Tag tag);
    boolean removeTag(Note note, Tag tag);
}
