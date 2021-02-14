package com.revature.chronicle.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.interceptors.AuthenticationInterceptor;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.services.NoteService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    
    private User mockUser;

    @MockBean
    private NoteService noteService;

	@MockBean
	AuthenticationInterceptor interceptor;


	@BeforeEach
	void initTest() {
	    try {
			when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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

        Tag tag1 = new Tag();
        //tag1.setTagID(1);
        tag1.setType("Technology");
        tag1.setValue("Angular");

        Tag tag2 = new Tag();
        //tag2.setTagID(2);
        tag2.setType("Technology");
        tag2.setValue("Java");

        Tag tag3 = new Tag();
        //tag3.setTagID(3);
        tag3.setType("Batch");
        tag3.setValue("1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Note note1 = new Note();
        //note1.setId(1);
        //note1.setUrl("http://note1.com/%22");
        note1.setDescription("A description 1");
        note1.setDate(new Date());
        note1.setUser("");
        note1.setTags(tags1);
        note1.setPrivate(false);
        mockNote = note1;

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Note note2 = new Note();
        //note2.setId(2);
        //note2.setUrl("http://note2.com/%22");
        note2.setDescription("A description 2");
        note2.setDate(new Date());
        note2.setUser("");
        note2.setTags(tags2);
        note2.setPrivate(false);

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

        Mockito.when(noteService.findAll(mockUser)).thenReturn(mockNotes);
        MvcResult result = mockMvc.perform(get("/notes/all").with(httpBasic("user","user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void shouldGetNotesByTag() throws Exception {
        ObjectMapper om = new ObjectMapper();

        Mockito.when(noteService.findAllNotesByTags(mockSingleTag, mockUser)).thenReturn(mockNotes);
        MvcResult result = mockMvc.perform(get("/notes/tags/{noteTags}","1:Technology:Angular")
                .with(httpBasic("user","user")))//Assuming words separated by '+'
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void shouldGetVideosByID() throws Exception {
        ObjectMapper om = new ObjectMapper();

        //Mockito.when(noteService.findById(1)).thenReturn(Optional.of(mockNote));
        Mockito.when(noteService.findById(1)).thenReturn(mockNote);
        MvcResult result = mockMvc.perform(get("/notes/id/{noteId}","1")
                .with(httpBasic("user","user")))//Assuming words separated by '+'
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void shouldGetAllTags() throws Exception {
        ObjectMapper om = new ObjectMapper();
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Topic");
        tagNames.add("Batch");

        Mockito.when(tagRepo.findByTypeIn(tagNames)).thenReturn(mockTags);
        MvcResult result = mockMvc.perform(get("/notes/available-tags")
                .with(httpBasic("user","user")))//Assuming words separated by '+'
                .andExpect(status().isOk())
                .andReturn();

        //Testing to ensure something is being returned
        Assert.assertNotNull(result.getResponse());
    }
}
