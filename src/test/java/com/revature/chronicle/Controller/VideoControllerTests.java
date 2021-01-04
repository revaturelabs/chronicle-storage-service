package com.revature.chronicle.Controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class VideoControllerTests {

	private MockMvc mockMvc;
	private List<Video> mockVideos;
	private List<Tag> mockTags;

	@Mock
	private VideoService videoService;

	@InjectMocks
	private VideoController videoController;

	@Before
	public void setup() {
		mockVideos = null;
		mockTags = null;

		MockitoAnnotations.openMocks(this);
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

		Video video1 = new Video();
		video1.setUrl("http://video1.com/%22");
		video1.setUser(user);
		video1.setDescription("A description");
		video1.setVideoTags(tags1);

		Set<Tag> tags2 = new HashSet<>();
		tags2.add(tag1);
		tags2.add(tag2);

		Video video2 = new Video();
		video2.setUrl("http://video2.com/%22");
		video2.setUser(user);
		video2.setDescription("A description");
		video2.setVideoTags(tags2);

		mockVideos.add(video1);
		mockVideos.add(video2);
		mockTags.add(tag1);
		mockTags.add(tag2);
	}

	@Test
	public void shouldGetAllVideos() throws Exception {

		when(videoService.findAll()).thenReturn(mockVideos);
		MvcResult result = mockMvc.perform(get("/videos/all"))
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
	public void shouldGetVideosByTag() throws Exception {
		when(videoService.findAllVideosByTags(mockTags)).thenReturn(mockVideos);
		MvcResult result = mockMvc.perform(get("/videos/tags/{videoTags}","Technology:Angular+Technology:Java"))//Assuming words separated by '+'
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