package com.revature.chronicle.daos;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class NoteService {
    @Autowired
    NoteRepo noteRepo;

    boolean save(Note note) {
        try {
            noteRepo.save(note);
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    List<Note> findAll() {
        try {
            return noteRepo.findAll();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    Note findById(int id) {
        try {
            Optional<Note> n = noteRepo.findById(id);
            if (n.isPresent()) {
                return n.get();
            }
            else {
                return new Note();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new Note();
        }
    }

    boolean deleteNote(Note note) {
        try {
            noteRepo.delete(note);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    // Needs to be implemented
//    List<Note> findByTag(Tag tag);
//    List<Note> findByTags(Set<Tag> tags);
}