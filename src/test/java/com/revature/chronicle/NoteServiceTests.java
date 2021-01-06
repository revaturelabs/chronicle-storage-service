package com.revature.chronicle;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.services.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

    }

    @Test
    public void shouldReturnANoteById(){

    }

    @Test
    public void shouldReturnNullIfNoNoteoFound(){

    }

    @Test
    public void shouldSaveANoteAndReturnTrue(){

    }

    @Test
    public void shouldFailToAddNoteAndReturnFalse(){

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
