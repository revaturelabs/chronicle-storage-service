package com.revature.chronicle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.services.NoteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTests {

    private List<Note> mockNotes;
    private List<Tag> mockTags;
    private Note mockNote;
    private List<Tag> mockSingleTag;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private TagRepo tagRepo;

    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Before
    public void security(){
        this.mockMvc = webAppContextSetup(wac)
                .build();
    }

    @Before
    public void setup() {

        mockNotes = new ArrayList<>();
        mockTags = new ArrayList<>();
        mockNote = new Note();
        mockSingleTag = new ArrayList<>();

        User user = new User();
        user.setUsername("TESTUSER");

        Tag tag1 = new Tag();
        tag1.setTagID(1);
        tag1.setName("Technology");
        tag1.setValue("Angular");

        Tag tag2 = new Tag();
        tag2.setTagID(2);
        tag2.setName("Technology");
        tag2.setValue("Java");

        Tag tag3 = new Tag();
        tag3.setTagID(3);
        tag3.setName("Batch");
        tag3.setValue("1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Note note1 = new Note();
        note1.setId(1);
        note1.setUrl("http://note1.com/%22");
        note1.setUser(user.getUsername());
        note1.setDescription("A description");
        note1.setTags(tags1);
        mockNote = note1;

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Note note2 = new Note();
        note2.setId(2);
        note2.setUrl("http://note2.com/%22");
        note2.setUser(user.getUsername());
        note2.setDescription("A description");
        note2.setTags(tags2);

        mockNotes.add(note1);
        mockNotes.add(note2);
        mockTags.add(tag1);
        mockTags.add(tag2);
        mockTags.add(tag3);
        mockSingleTag.add(tag1);
    }

    @Test
    public void shouldGetAllNotes() throws Exception {
        ObjectMapper om = new ObjectMapper();

        Mockito.when(noteService.findAll()).thenReturn(mockNotes);
        MvcResult result = mockMvc.perform(get("/notes/all").with(httpBasic("user","user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());

        Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockNotes));
    }

    @Test
    public void shouldGetNotesByTag() throws Exception {
        ObjectMapper om = new ObjectMapper();

        Mockito.when(noteService.findAllNotesByTags(mockSingleTag)).thenReturn(mockNotes);
        MvcResult result = mockMvc.perform(get("/notes/tags/{noteTags}","1:Technology:Angular")
                .with(httpBasic("user","user")))//Assuming words separated by '+'
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());

        Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockNotes));
    }

    @Test
    public void shouldGetVideosByID() throws Exception {
        ObjectMapper om = new ObjectMapper();

        Mockito.when(noteService.findById(1)).thenReturn(java.util.Optional.ofNullable(mockNote));
        MvcResult result = mockMvc.perform(get("/notes/id/{noteId}","1")
                .with(httpBasic("user","user")))//Assuming words separated by '+'
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());

        Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockNote));
    }

    @Test
    public void shouldGetAllTags() throws Exception {
        ObjectMapper om = new ObjectMapper();
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Technology");
        tagNames.add("Batch");

        Mockito.when(tagRepo.findByNameIn(tagNames)).thenReturn(mockTags);
        MvcResult result = mockMvc.perform(get("/notes/available-tags")
                .with(httpBasic("user","user")))//Assuming words separated by '+'
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());

        Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockTags));
    }
}
