package com.revature.chronicle.Controller;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.services.NoteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTests {

    private List<Note> mockNotes;
    private List<Tag> mockTags;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @BeforeEach
    public void setup() {

        mockNotes = new ArrayList<>();
        mockTags = new ArrayList<>();

        User user = new User();
        user.setUsername("TESTUSER");

        Tag tag1 = new Tag();
        tag1.setName("Technology");
        tag1.setValue("Angular");

        Tag tag2 = new Tag();
        tag2.setName("Technology");
        tag2.setValue("Java");

        Tag tag3 = new Tag();
        tag3.setName("Batch");
        tag3.setValue("1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Note note1 = new Note();
        note1.setUrl("http://note1.com/%22");
        note1.setUser(user);
        note1.setDescription("A description");
        note1.setNoteTags(tags1);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Note note2 = new Note();
        note2.setUrl("http://note2.com/%22");
        note2.setUser(user);
        note2.setDescription("A description");
        note2.setNoteTags(tags2);

        mockNotes.add(note1);
        mockNotes.add(note2);
        mockTags.add(tag1);
        mockTags.add(tag2);
    }

    @Test
    public void shouldGetAllNotes() throws Exception {

        when(noteService.findAll()).thenReturn(mockNotes);
        MvcResult result = mockMvc.perform(get("/notes/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        int actual = result.getResponse().getStatus();

        int expected = 200;

        Assert.assertEquals(actual, expected); //Testing to ensure the get request was successful

        //Testing to ensure something is being returned
        Assert.assertTrue(result.getResponse().getContentAsString().length() > 0);

        //Testing to ensure the response is formatted properly
        Assert.assertNotNull(result.getResponse().getHeader("Content-Type").equals("application/json;charset=UTF-8"));
    }

    @Test
    public void shouldGetNotesByTag() throws Exception {
        when(noteService.findAllNotesByTags(mockTags)).thenReturn(mockNotes);
        MvcResult result = mockMvc.perform(get("/notes/tags/{noteTags}","Technology:Angular+Technology:Java"))//Assuming words separated by '+'
                .andExpect(status().isOk())
                .andReturn();

        int actual = result.getResponse().getStatus();

        int expected = 200;

        Assert.assertEquals(actual, expected); //Testing to ensure the get request was successful

        //Testing to ensure something is being returned
        Assert.assertTrue(result.getResponse().getContentAsString().length() > 0);

        //Testing to ensure the response is formatted properly
        Assert.assertNotNull(result.getResponse().getHeader("Content-Type").equals("application/json;charset=UTF-8"));
    }
}
