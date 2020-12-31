package com.revature.chronicle.services;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
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

    /**
     * Finds all Videos that have the all of provided tags.
     * @param tags the tags provided by the user
     * @return a list of videos that have all tags
     */
    public List<Note> findAllNotesByTags(List<Tag> tags){
        System.out.println("Entered service method");
        List<Note> desiredNotes = new ArrayList<>();
        int offset = 0;
        final int LIMIT = 50;
        do{
            //Query database for first 50 most recent results
            //Since date is a timestamp it should account for hours, mins, secs as well ensuring the order of the list
            List<Note> notes = noteRepo.findNotesWithOffsetAndLimit(offset,LIMIT);
            System.out.println(notes.size());

            //Check if notes is empty as no more records exist
            if(notes.size()>0){
                //Iterate through 50 results
                for(Note note:notes){
                    //Check to see if result has all passed in tags,if so add to desiredVideos
                    if(note.getNoteTags().containsAll(tags)){
                        System.out.println("Adding note");
                        desiredNotes.add(note);
                    }
                    else{
                        System.out.println("Note not found");
                    }
                }
            }
            else{
                break;
            }
            offset+= notes.size();
        }
        while(desiredNotes.size() < 50 && desiredNotes.size()>0);

        //Find way to sort by return if it doesn't keep by recent order
        return desiredNotes;
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