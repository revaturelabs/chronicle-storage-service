package com.revature.chronicle.Controller;




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

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(classes ={S3ControllerTest.class})
@RunWith(SpringRunner.class)
public class S3ControllerTest {

	//Mocker object
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMultipartFile file;


	@Before
	public void setup(){
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}


	@Test
	public void givenFormData_whenFileUpload_theReturnOKStatus() throws Exception {

		//given a mock MultipartFile to pass into the controller
		file = new MockMultipartFile(
				"file",
				"test.txt",
				MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes()
		);

		//mocking the json discription from front end
		JSONObject json = new JSONObject();
		json.put("testing", "123");
		json.put("test2","here we go");

		//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("file", file.getOriginalFilename());
		params.add("json", json.toString());


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
		result.andExpect(mvcResult -> Assert.assertTrue("The file did contain content",file.getSize() > 0));
	}


	@Test
	public void givenEmpty_GivenNoSuchFile_WhenFileUpload_shouldReturnError() throws Exception {

	//given a mock MultipartFile to pass into the controller
	file = new MockMultipartFile(
				"file",
				"test.txt",
				MediaType.TEXT_PLAIN_VALUE,
				"".getBytes()
		);


	//mocking the json discription from front end
	JSONObject json = new JSONObject();
	json.put("testing", "123");
	json.put("test2","here we go");


	//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
	final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	params.add("file", (file.getBytes().toString()));
	params.add("json", json.toString());


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
	result.andExpect(mvcResult -> Assert.assertTrue("The file given was empty or doesnt exist",file.isEmpty()));
	}
}
