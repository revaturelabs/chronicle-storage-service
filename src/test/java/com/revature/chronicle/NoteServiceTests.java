package com.revature.chronicle;

import com.amazonaws.services.dynamodbv2.xspec.N;
import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.services.NoteService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoteServiceTests {
    @Mock
    private NoteRepo repo;

    @InjectMocks
    private NoteService service;

    @Test
    public void shouldReturnAListOfAllNotes(){
        List<Note> notes = new ArrayList();
        notes.add(new Note(1,"www.note.com","A description",new Date(), new User(), new HashSet<Tag>()));

        when(repo.findAll()).thenReturn(notes);

        List<Note> result = service.findAll();

        Assert.assertTrue(notes.equals(result));

        verify(repo).findAll();

    }

    @Test
    public void shouldReturnANoteById(){
        Note note = new Note(1,"www.note.com","a description",new Date(),new User(), new HashSet<Tag>());
        when(repo.findById(1)).thenReturn(Optional.of(note));
        Note result = service.findById(1);
        Assert.assertTrue(note.equals(result));
        verify(repo).findById(1);
    }

    @Test
    public void shouldReturnNullIfNoNoteFound(){
        when(repo.findById(2)).thenReturn(Optional.empty());
        Note result = service.findById(2);
        Assert.assertFalse(result==null);
        verify(repo).findById(2);
    }

    @Test
    public void shouldSaveANoteAndReturnTrue(){
        Note note = new Note(1,"www.note.com","a description",new Date(),new User(), new HashSet<Tag>());
        when(repo.save(note)).thenReturn(note);
        boolean result = service.save(note);
        Assert.assertTrue(result);
        verify(repo).save(note);
    }

    @Test
    public void shouldFailToAddNoteAndReturnFalse(){
        when(repo.save(null)).thenReturn(null);
        boolean result = service.save(null);
        Assert.assertFalse(result);
        verify(repo).save(null);
    }

    @Test
    public void shouldReturnOnlyNotesThatMatchAllGivenTags(){

    }

    @Test
    public void shouldReturnAnEmptyListIfNoNotesAreFound(){

    }

    @Test
    public void shouldReturnAnEmptyListIfTagsAreEmpty(){

    }
}
