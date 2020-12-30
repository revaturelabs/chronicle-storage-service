package com.revature.chronicle.services;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle business logic surrounding data access layer for notes
 */
@Service
public class NoteService {
    @Autowired
    private NoteRepo noteRepo;

    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public boolean save(Note note) {
        try {
            noteRepo.save(note);
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Note> findAll() {
        try {
            return noteRepo.findAll();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Note findById(int id) {
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

    public boolean deleteNote(Note note) {
        try {
            noteRepo.delete(note);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}