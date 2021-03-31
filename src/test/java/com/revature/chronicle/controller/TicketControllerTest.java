package com.revature.chronicle.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.chronicle.interceptors.AuthenticationInterceptor;
import com.revature.chronicle.models.Notification;
import com.revature.chronicle.models.Ticket;
import com.revature.chronicle.services.NotificationService;
import com.revature.chronicle.services.TicketService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {
	
	private Ticket mockTicket1;
	private Ticket mockTicket2;
	private Ticket mockTicket3;
	private List<Ticket> mockMyList;
	private JSONObject json;
	private Notification mockNotification;
	private MediaType APPLICATION_JSON_VALUE;
	private String requestJson;
	
	
	@Autowired
    private WebApplicationContext wac;
	
	@MockBean
	private TicketService ticketService;
	
	@MockBean
	private NotificationService notificationService;

	@Autowired
    private MockMvc mockMvc;
    
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
    public void setup() throws JSONException, IOException {
    	MockitoAnnotations.openMocks(this);
    	mockTicket1 =  new Ticket(1,"1","1","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
		mockTicket2 =  new Ticket(2,"10","10","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
		mockTicket3 =  new Ticket(3,"100","100","core java", "a","time","endTime","link1","pass1",10,"url1","status1","Id1","comments");
		mockMyList= new ArrayList<>();
		mockMyList.add(mockTicket1);
		mockMyList.add(mockTicket2);
		mockMyList.add(mockTicket3);
		
		json = new JSONObject();
		json.put("ticketID", "1");
		json.put("issuerID", "1");
		json.put("editorID", "1");
		json.put("topic", "1");
		json.put("description", "test description");
		json.put("startTime", "20210101");
		json.put("endTime", "20210101");
		json.put("zoomLink", "http://www.google.com");
		json.put("zoomPasscode", "test passcode");
		json.put("clipID", "1");
		json.put("clipUrl", "http://www.google.com");
		json.put("ticketStatus", "in progress");
		json.put("identifier", "test original video");
		json.put("rejectcomment", "test reject comment");
		
		mockNotification = new Notification(1, "1", "1", mockTicket1,
				new Date(20210101), "test note" );
		
		APPLICATION_JSON_VALUE = MediaType.APPLICATION_JSON;
//				new MediaType(MediaType.APPLICATION_JSON.getType(), 
//						MediaType.APPLICATION_JSON.getSubtype());
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    requestJson=ow.writeValueAsString(json);
    }

	@Test
	void testFindAll() throws Exception{
		
		ObjectMapper om = new ObjectMapper();
		Mockito.when(ticketService.findAll()).thenReturn(mockMyList);
		MvcResult result = mockMvc.perform(get("/ticket/all"))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		Assert.assertNotNull(result.getResponse());
		
	}

//	@Test
//	void testSave() {
//		
//	}

	@Test
	public void testSaveAll() throws Exception{
	
		
	    
	    
		Mockito.when(ticketService.saveAll(mockMyList)).thenReturn(true);
		MvcResult result = mockMvc.perform(post("/ticket/saveall")
				.contentType(APPLICATION_JSON_VALUE).content(requestJson))
				.andReturn();
		
		Assert.assertNotNull(result);
	}

	@Test
	void testUpdate() throws Exception {
		Mockito.when(ticketService.update(mockTicket1)).thenReturn(true);
		Mockito.when(notificationService.createNotification(mockNotification)).thenReturn(mockNotification);
		MvcResult result = mockMvc.perform(post("/ticket/update")
				.contentType(APPLICATION_JSON_VALUE).content(requestJson))
				.andReturn();
		
		Assert.assertNotNull(result);
	}

}
