package com.revature.chronicle.daos;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for note data
 */
public interface NoteRepo extends JpaRepository<Note, Integer> {
//    // Needs to be implemented
//    List<Note> findByTag(Tag tag);                    Causes error when not commented out.
//    boolean addTag(Note note, Tag tag);               Causes error when not commented out.
//    boolean removeTag(Note note, Tag tag);            Causes error when not commented out.
//
//    // Implemented and functional
//    Note getNote(int id);   ->   Optional<Note> findById(Integer id);
//    List<Note> getNotes();    ->     List<Note> findAll();
//    boolean addNote(Note note);         ->     void save();
//    boolean updateNote(Note note);         ->     void save();
//    boolean deleteNote(Note note);        ->    void delete();

}
