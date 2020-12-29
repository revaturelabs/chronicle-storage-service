package com.revature.chronicle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ChronicleApplicationTests {

	private vidoeDao vd;
	private MockMvc mockMvc;

	@Before
	public void setup()
	{
		vd = mock(videoDao.class);
	}

	@Test
	public void shouldGetAllVideos() throws Exception {
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
		MvcResult result = mockMvc.perform(get("/videos/tags/{videoTags}","Java+JDBC"))//Assuming words separated by '+'
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
