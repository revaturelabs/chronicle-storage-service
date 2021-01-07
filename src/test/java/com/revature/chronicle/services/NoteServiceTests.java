package com.revature.chronicle.services;

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
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note(1,"www.note.com","A description",new Date(), new User(), new HashSet<Tag>()));

        when(repo.findAll()).thenReturn(notes);

        List<Note> result = service.findAll();

        Assert.assertEquals(notes, result);

        verify(repo).findAll();

    }

    @Test
    public void shouldReturnANoteById(){
        Note note = new Note(1,"www.note.com","a description",new Date(),new User(), new HashSet<Tag>());
        when(repo.findById(1)).thenReturn(Optional.of(note));
        Optional<Note> result = service.findById(1);
        Assert.assertTrue(result.isPresent());
        if (result.isPresent()) {
            Assert.assertEquals(note, result.get());
        }
        verify(repo).findById(1);
    }

    @Test
    public void shouldNotReturnNoteById(){
        when(repo.findById(2)).thenReturn(Optional.empty());
        Optional<Note> result = service.findById(2);
        Assert.assertFalse(result.isPresent());
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
        when(repo.save(null)).thenThrow(IllegalArgumentException.class);
        boolean result = service.save(null);
        Assert.assertFalse(result);
        verify(repo).save(null);
    }

    @Test
    public void shouldReturnOnlyNotesThatMatchAllGivenTags(){
        Tag tag1 = new Tag(1,"Technology","Angular");
        Tag tag2 = new Tag(2,"Technology","Java");
        Tag tag3 = new Tag(3,"Batch","1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Note note1 = new Note(1,"http://note.com","A description",new Date(),new User(),tags1);
        Note note2 = new Note(2,"http://note.com","A description",new Date(),new User(),tags2);

        when(repo.findNotesWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Note>(Arrays.asList(note1,note2)));
        List<Note> result = service.findAllNotesByTags(Arrays.asList(tag1,tag3));
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(note1) && !result.contains(note2));
        verify(repo).findNotesWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfNoNotesAreFound(){
        Tag tag1 = new Tag(1,"Technology","Angular");
        Tag tag2 = new Tag(2,"Technology","Java");
        Tag tag3 = new Tag(3,"Batch","1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Note note1 = new Note(1,"http://note.com","A description",new Date(),new User(),tags1);
        Note note2 = new Note(2,"http://note.com","A description",new Date(),new User(),tags2);

        when(repo.findNotesWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Note>(Arrays.asList(note1,note2)));
        List<Note> result = service.findAllNotesByTags(Arrays.asList(tag2,tag3));
        Assert.assertTrue(result.isEmpty());
        verify(repo).findNotesWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfTagsAreEmpty(){
        Tag tag1 = new Tag(1,"Technology","Angular");
        Tag tag2 = new Tag(2,"Technology","Java");
        Tag tag3 = new Tag(3,"Batch","1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Note note1 = new Note(1,"http://note.com","A description",new Date(),new User(),tags1);
        Note note2 = new Note(2,"http://note.com","A description",new Date(),new User(),tags2);

        when(repo.findNotesWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Note>());
        List<Note> result = service.findAllNotesByTags(new ArrayList<Tag>());
        Assert.assertTrue(result.isEmpty());
        verify(repo).findNotesWithOffsetAndLimit(0,50);
    }
}
