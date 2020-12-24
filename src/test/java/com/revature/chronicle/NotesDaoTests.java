package com.revature.chronicle;

import com.revature.chronicle.daos.NoteDAO;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class NotesDaoTests {
    private NoteDAO dao;

    @Test
    void shouldGetNoteById() {
        // Create user
        User user = new User();
        user.setUserID(-1);
        user.setUsername("fakeUser");

        // Create note
        Note note = new Note();
        note.setNoteID(-1);
        note.setUrl("fakeUrl");
        note.setDescription("blah blah blah blah");
        note.setUser(user);

        // Add note to database
        dao.addNote(note);

        // Get note from database
        Note retrievedNote = dao.getNote(note.getNoteID());
        Assert.notNull(retrievedNote, "");
    }

    @Test
    void shouldNotGetNoteById() {
        int badId = -2;
        Note note = dao.getNote(badId);
        Assert.isNull(note, "");
    }

    @Test
    void shouldGetNotesByTag() {
        // Create user
        User user = new User();
        user.setUserID(-1);
        user.setUsername("fakeUser");

        // Create tag
        Tag tag = new Tag();
        tag.setTagID(-1);
        tag.setName("topic");
        tag.setValue("angular");

        // Create note with tag
        Note noteWithTag = new Note();
        noteWithTag.setNoteID(-1);
        noteWithTag.setUrl("fakeUrl");
        noteWithTag.setDescription("blah blah blah blah");
        noteWithTag.setUser(user);

        // Add note to db
        dao.addNote(noteWithTag);

        // Add tag to note
        dao.addTag(noteWithTag, tag);

        // Create note without tag
        Note noteWithoutTag = new Note();
        noteWithoutTag.setNoteID(-2);
        noteWithoutTag.setUrl("fakeUrl");
        noteWithoutTag.setDescription("blah blah blah blah");
        noteWithoutTag.setUser(user);

        // Get list of notes with tag
        List<Note> notes = dao.getNotes(tag);

        // Verify that each note has the tag
        for (Note note: notes) {
            // Iterate through tags to find matching tag
            boolean match = false;
            for (Tag t: note.getNote_tags()) {
                if (t.equals(tag)) {
                    match = true;
                    break;
                }
            }

            // Verify that note has tag
            Assert.isTrue(match, "");
        }
    }

    @Test
    void shouldGetAllNotes() {
        List<Note> notes = dao.getNotes();
        Assert.notNull(notes, "");
    }

    @Test
    void shouldAddNote() {
        // Create user
        User user = new User();
        user.setUserID(-1);
        user.setUsername("fakeUser");

        // Create note
        Note note = new Note();
        note.setNoteID(-1);
        note.setUrl("fakeUrl");
        note.setDescription("blah blah blah blah");
        note.setUser(user);

        // Add note to database
        dao.addNote(note);

        // Verify that the note is in the database
        Assert.notNull(dao.getNote(note.getNoteID()), "");
    }

    @Test
    void shouldNotAddNote() {
        // Create user
        User user = new User();
        user.setUserID(-1);
        user.setUsername("fakeUser");

        // Create note
        Note note = new Note();
        note.setNoteID(-1);
        note.setUrl("fakeUrl");
        note.setDescription("blah blah blah blah");
        note.setUser(user);

        // Add note to database
        dao.addNote(note);

        // Verify that you cannot add duplicate note to database
        Assert.isTrue(dao.addNote(note), "");
    }

    @Test
    void shouldUpdateNote() {
        // Create user
        User user = new User();
        user.setUserID(-1);
        user.setUsername("fakeUser");

        // Create note
        Note note = new Note();
        note.setNoteID(-1);
        note.setUrl("fakeUrl");
        note.setDescription("blah blah blah blah");
        note.setUser(user);

        // Add note to database
        dao.addNote(note);

        // Edit note
        String newUrl = "newUrl";
        note.setUrl(newUrl);

        // Update note
        dao.updateNote(note);

        // Get updated note from the database
        Note updatedNote = dao.getNote(note.getNoteID());

        // Verify that the note has been updated
        Assert.isTrue(newUrl.equals(updatedNote.getUrl()), "");
    }

    @Test
    void shouldDeleteNote() {
        // Create user
        User user = new User();
        user.setUserID(-1);
        user.setUsername("fakeUser");

        // Create note
        Note note = new Note();
        note.setNoteID(-1);
        note.setUrl("fakeUrl");
        note.setDescription("blah blah blah blah");
        note.setUser(user);

        // Add note to database
        dao.addNote(note);

        // Delete the note from the database
        dao.deleteNote(note);

        // Verify that note is not in the database
        Assert.isNull(dao.getNote(note.getNoteID()), "");
    }

    @Test
    void shouldAddTagToNote() {
        Note noteToBeModified = new Note();
        Tag tagToBeAdded = new Tag();

        List<Note> beforeAddingTag = dao.getNotes(tagToBeAdded);
        for (Note note: beforeAddingTag) {
            Assert.isTrue(!note.equals(noteToBeModified), "");
        }

        dao.addTag(noteToBeModified, tagToBeAdded);

        List<Note> afterAddingTag = dao.getNotes(tagToBeAdded);
        Note taggedNote = null;
        for (Note note: afterAddingTag) {
            if(note.equals(noteToBeModified)) {
                taggedNote = note;
                break;
            }
        }

        Assert.notNull(taggedNote, "");
    }

    @Test
    void shouldRemoveTagFromNote() {
        Note noteToBeModified = new Note();
        Tag tagToBeRemoved = new Tag();

        Note taggedNote = null;
        List<Note> beforeRemovingTag = dao.getNotes(tagToBeRemoved);
        for (Note note: beforeRemovingTag) {
            if(note.equals(noteToBeModified)) {
                taggedNote = note;
                break;
            }
        }
        Assert.notNull(taggedNote, "");

        dao.removeTag(noteToBeModified, tagToBeRemoved);

        List<Note> afterRemovingTag = dao.getNotes(tagToBeRemoved);

        for (Note note: afterRemovingTag) {
            Assert.isTrue(!note.equals(noteToBeModified), "");
        }
    }

}
