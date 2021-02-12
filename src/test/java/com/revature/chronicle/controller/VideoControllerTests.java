//package com.revature.chronicle.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.revature.chronicle.daos.TagRepo;
//import com.revature.chronicle.models.Tag;
//import com.revature.chronicle.models.User;
//import com.revature.chronicle.models.Video;
//import com.revature.chronicle.services.VideoService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class VideoControllerTests {
//
//	private List<Video> mockVideos;
//	private List<Tag> mockTags;
//	private Video mockVideo;
//	private List<Tag> mockSingleTag;
//
//	@Autowired
//	private WebApplicationContext wac;
//
//	@MockBean
//	private TagRepo tagRepo;
//
//	private MockMvc mockMvc;
//
//	@MockBean
//	private VideoService videoService;
//
//	@Before
//	public void security(){
//		this.mockMvc = webAppContextSetup(wac)
//				.build();
//	}
//
//	@Before
//	public void setup() {
//
//		mockVideos = new ArrayList<>();
//		mockTags = new ArrayList<>();
//		mockVideo = new Video();
//		mockSingleTag = new ArrayList<>();
//
//		User user = new User();
//
//		Tag tag1 = new Tag();
//		tag1.setTagID(1);
//		tag1.setType("Technology");
//		tag1.setValue("Angular");
//
//		Tag tag2 = new Tag();
//		tag2.setTagID(2);
//		tag2.setType("Technology");
//		tag2.setValue("Java");
//
//		Tag tag3 = new Tag();
//		tag3.setTagID(3);
//		tag3.setType("Batch");
//		tag3.setValue("1120-August");
//
//		List<Tag> tags1 = new ArrayList<>();
//		tags1.add(tag1);
//		tags1.add(tag3);
//
//		Video video1 = new Video();
//		video1.setUrl("http://video1.com/%22");
//		video1.setDescription("A description");
//		video1.setTags(tags1);
//		video1.setId(1);
//		mockVideo = video1;
//
//		List<Tag> tags2 = new ArrayList<>();
//		tags2.add(tag1);
//		tags2.add(tag2);
//
//		Video video2 = new Video();
//		video2.setUrl("http://video2.com/%22");
//		video2.setDescription("A description");
//		video2.setTags(tags2);
//		video2.setId(2);
//
//		mockVideos.add(video1);
//		mockVideos.add(video2);
//		mockTags.add(tag1);
//		mockTags.add(tag2);
//		mockTags.add(tag3);
//		mockSingleTag.add(tag1);
//	}
//
//	@Test
//	public void shouldGetAllVideos() throws Exception {
//		ObjectMapper om = new ObjectMapper();
//
//		Mockito.when(videoService.findAll()).thenReturn(mockVideos);
//		MvcResult result = this.mockMvc.perform(get("/videos/all").with(httpBasic("user","user")))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andReturn();
//
//		//Testing to ensure something is being returned
//		Assert.assertNotNull(result.getResponse());
//
//		Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockVideos));
//	}
//
//	@Test
//	public void shouldGetVideosByTag() throws Exception {
//		ObjectMapper om = new ObjectMapper();
//
//		Mockito.when(videoService.findAllVideosByTags(mockSingleTag)).thenReturn(mockVideos);
//		MvcResult result = mockMvc.perform(get("/videos/tags/{videoTags}","1:Technology:Angular")
//				.with(httpBasic("user","user")))//Assuming words separated by '+'
//				.andExpect(status().isOk())
//				.andReturn();
//
//		//Testing to ensure something is being returned
//		Assert.assertNotNull(result.getResponse());
//
//		Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockVideos));
//	}
//
//	@Test
//	public void shouldGetVideosByID() throws Exception {
//		ObjectMapper om = new ObjectMapper();
//
//		Mockito.when(videoService.findById(1)).thenReturn(java.util.Optional.ofNullable(mockVideo));
//		MvcResult result = mockMvc.perform(get("/videos/id/{videoId}","1")
//				.with(httpBasic("user","user")))//Assuming words separated by '+'
//				.andExpect(status().isOk())
//				.andReturn();
//
//		//Testing to ensure something is being returned
//		Assert.assertNotNull(result.getResponse());
//
//		Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockVideo));
//	}
//
//	@Test
//	public void shouldGetAllTags() throws Exception {
//		ObjectMapper om = new ObjectMapper();
//		List<String> tagNames = new ArrayList<>();
//		tagNames.add("Topic");
//		tagNames.add("Batch");
//
//		Mockito.when(tagRepo.findByNameIn(tagNames)).thenReturn(mockTags);
//		MvcResult result = mockMvc.perform(get("/videos/available-tags")
//				.with(httpBasic("user","user")))//Assuming words separated by '+'
//				.andExpect(status().isOk())
//				.andReturn();
//
//		//Testing to ensure something is being returned
//		Assert.assertNotNull(result.getResponse());
//
//		Assert.assertEquals(result.getResponse().getContentAsString(),om.writeValueAsString(mockTags));
//	}
//
//}
