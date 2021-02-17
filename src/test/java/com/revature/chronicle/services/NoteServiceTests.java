package com.revature.chronicle.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;

@SpringBootTest
public class NoteServiceTests {
    @Mock
    private NoteRepo repo;

    @InjectMocks
    private User mockUser;
    
    @InjectMocks
    private NoteService service;

    @Test
    public void shouldReturnAListOfAllNotes(){
        List<Note> notes = new ArrayList<Note>();
        //notes.add(new Note(1,"www.note.com","a title","A description",new Date(), "", new ArrayList<Tag>(), 0));
        notes.add(new Note("A description",new Date(), "", "", new ArrayList<Tag>(),false));

        when(repo.findAll()).thenReturn(notes);

        List<Note> result = service.findAll(mockUser);

        Assert.assertNotNull(result);
    }

    @Test
    public void shouldReturnANoteById(){
        //Note note = new Note(1,"www.note.com","a title","a description",new Date(),"", new ArrayList<Tag>(), 0);
        Note note = new Note("A description",new Date(),"", "", new ArrayList<Tag>(),false);
        when(repo.findById(1)).thenReturn(Optional.of(note));
        Optional<Note> result = Optional.ofNullable(service.findById(1));
        Assert.assertNotNull(result);
        verify(repo).findById(1);
    }

    @Test
    public void shouldNotReturnNoteById(){
        when(repo.findById(2)).thenReturn(Optional.empty());
        Optional<Note> result = Optional.ofNullable(service.findById(2));
        Assert.assertFalse(result.isPresent());
        verify(repo).findById(2);
    }

    @Test
    public void shouldSaveANoteAndReturnTrue(){
        //Note note = new Note(1,"www.note.com","a title","a description",new Date(),"", new ArrayList<Tag>(), 0);
        Note note = new Note("A description",new Date(),"", "", new ArrayList<Tag>(),false);
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
        Tag tag1 = new Tag("Technology","Angular");
        Tag tag2 = new Tag("Technology","Java");
        Tag tag3 = new Tag("Batch","1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);

        //Note note1 = new Note(1,"http://note.com","a title","A description",new Date(),"",tags1, 0);
        //Note note2 = new Note(2,"http://note.com","a title","A description",new Date(),"",tags2, 0);
        Note note1 = new Note("A description 1",new Date(),"", "", tags1,false);
        Note note2 = new Note("A description 2",new Date(),"", "", tags2,false);
        
        note1.setTags(tags1);
        note2.setTags(tags2);       
        mockUser.setRole("ROLE_ADMIN");

        when(repo.findNotesWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Note>(Arrays.asList(note1,note2)));
        List<Note> result = service.findAllNotesByTags(Arrays.asList(tag1,tag3), mockUser);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(note1) && !result.contains(note2));
        verify(repo).findNotesWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfNoNotesAreFound(){
        Tag tag1 = new Tag("Technology","Angular");
        Tag tag2 = new Tag("Technology","Java");
        Tag tag3 = new Tag("Batch","1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);

        //Note note1 = new Note(1,"http://note.com","a title","A description",new Date(),"",tags1);
        //Note note2 = new Note(2,"http://note.com","a title","A description",new Date(),"",tags2);
        Note note1 = new Note("A description 1",new Date(),"", "", tags1,false);
        Note note2 = new Note("A description 2",new Date(),"", "", tags2,false);

        note1.setTags(tags1);
        note2.setTags(tags2);

        when(repo.findNotesWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Note>(Arrays.asList(note1,note2)));
        List<Note> result = service.findAllNotesByTags(Arrays.asList(tag2,tag3), mockUser);
        Assert.assertTrue(result.isEmpty());
        verify(repo).findNotesWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfTagsAreEmpty(){
        Tag tag1 = new Tag("Technology","Angular");
        Tag tag2 = new Tag("Technology","Java");
        Tag tag3 = new Tag("Batch","1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);
        //Note note1 = new Note(1,"http://note.com","a title","A description",new Date(),"",tags1, 0);
        //Note note2 = new Note(2,"http://note.com","a title","A description",new Date(),"",tags2, 0);
        Note note1 = new Note("A description 1",new Date(),"", "", tags1,false);
        Note note2 = new Note("A description 2",new Date(),"", "", tags2,false);

        when(repo.findNotesWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Note>());
        List<Note> result = service.findAllNotesByTags(new ArrayList<Tag>(), mockUser);
        Assert.assertTrue(result.isEmpty());
        verify(repo).findNotesWithOffsetAndLimit(0,50);
    }
}
