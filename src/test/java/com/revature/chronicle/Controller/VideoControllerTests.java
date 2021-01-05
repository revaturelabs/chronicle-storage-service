package com.revature.chronicle.Controller;

import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.VideoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class VideoControllerTests {

	private List<Video> mockVideos;
	private List<Tag> mockTags;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VideoService videoService;

	@BeforeEach
	public void setup() {

		mockVideos = new ArrayList<>();
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

		Mockito.when(videoService.findAll()).thenReturn(mockVideos);
		MvcResult result = this.mockMvc.perform(get("/videos/all"))
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
