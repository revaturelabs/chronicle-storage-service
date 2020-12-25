package com.revature.chronicle.Controller;



import com.google.common.collect.Multimap;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(classes ={S3ControllerTest.class})
@RunWith(SpringRunner.class)
public class S3ControllerTest {

	//Mocker object
	private MockMvc mvc;

	private MockMultipartFile file;
	private MockMultipartFile file2;

	private JSONObject json = new JSONObject();

	final MultiValueMap<String, String> params  = new LinkedMultiValueMap<>();

	@Autowired
	private WebApplicationContext webApplicationContext;




	@Before
	public void setup() throws JSONException, IOException {

		//given a mock MultipartFile an existing file to pass into the controller
		file = file = new MockMultipartFile(
				"file",
				"test.txt",
				MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes()
		);

		//given a mock MultipartFile with a non existent file to pass into the controller
		file2 = new MockMultipartFile(
				"file",
				"falsefilename",
				MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes()
		);

		//mocking the json description from front end
		 json.put("testing", "123");
		 json.put("test2","here we go");


		//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
		params.add("file", file.getBytes().toString());
		params.add("json", json.toString());



		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}


	@Test
	public void givenFormData_whenFileUpload_theReturnOKString() throws Exception {

		//this is mocking the servlet being called and being passed the needed parameters for desired services
		//when
		final ResultActions result = mvc.perform(multipart("/s3/upload")
				.params(params)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.characterEncoding("utf-8")
				.content(params.toString()));


		//then stating what is expected as a response from the servlet
		result.andDo(print());
		result.andExpect(status().isOk());



	}


	@Test
	public void givenBadFilePath_WhenFileUpload_shouldReturnError() throws Exception {

	//this is mocking the servlet being called and being passed the needed parameters for desired services
	//when
	final ResultActions result = mvc.perform(multipart("/s3/upload")
			.params(params)
			.accept(MediaType.MULTIPART_FORM_DATA_VALUE)
			.characterEncoding("utf-8")
			.content(params.toString()));

	//then stating what is expected as a response from the servlet
	result.andDo(print());
	result.andExpect(status().is4xxClientError());
	}

}
