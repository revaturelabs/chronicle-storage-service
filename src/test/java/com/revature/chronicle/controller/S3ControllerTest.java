package com.revature.chronicle.Controller;

import com.revature.chronicle.services.NoteService;
import com.revature.chronicle.services.S3FileService;
import com.revature.chronicle.services.VideoService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class S3ControllerTest {

	//Mocker object
	@Autowired
	private  MockMvc mock;

	private  MockMultipartFile file1;
	private  MockMultipartFile file2;
	private  MockMultipartFile file3;
	private  MockMultipartFile file4;
	private  JSONObject json;
	private  MultiValueMap<String, String> params;

	@MockBean
	private  S3FileService s3FileMock;

	@MockBean
	private  VideoService videoMock;

	@MockBean
	private  NoteService noteMock;

	@Before
	public void setup() throws JSONException {
		s3FileMock = mock(S3FileService.class);
		videoMock = mock(VideoService.class);
		noteMock = mock(NoteService.class);

		mock = MockMvcBuilders.standaloneSetup(new FileUploadController(s3FileMock, videoMock, noteMock)).build();

		//given a mock text MultipartFile to pass into the controller
		file1 = new MockMultipartFile(
				"file",
				"test.txt",
				"text",
				"Hello, World!".getBytes()
		);

		//given a mock video MultipartFile to pass into the controller
		file2 = new MockMultipartFile(
				"file",
				"test.mp4",
				"video",
				"video".getBytes()
		);

		//given a nonexistent MultipartFile to pass into the controller
		file3 = new MockMultipartFile(
				" ", new byte[0]
		);

		//given a mock MultipartFile of wrong type to pass into the controller
		file4 = new MockMultipartFile(
				"file",
				"test.txt",
				"text/plain",
				"Hello, World!".getBytes()
		);


		json = new JSONObject();
		json.put("testing", "123");
		json.put("test2","here we go");

		params = new LinkedMultiValueMap<>();
	}
	@Test
	public void givenFormData_whenFileUpload_theReturnOKStatus() throws Exception {

		//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
		params.add("file", file1.getBytes().toString());
		params.add("name", json.toString());


		//this is mocking the servlet being called and being passed the needed parameters for desired services
		final ResultActions result = mock.perform(multipart("/file/submit").file(file1)
				.params(params)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andDo(print());


		//then stating what is expected as a response from the servlet
		result.andExpect(status().isOk());
		result.andExpect(mvcResult -> Assert.assertTrue("The file did contain content",file1.getSize() > 0));
	}

	@Test
	public void givenFormData_whenVideoUpload_theReturnOKStatus() throws Exception {

		//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
		params.add("file", file2.getBytes().toString());
		params.add("name", json.toString());


		//this is mocking the servlet being called and being passed the needed parameters for desired services
		final ResultActions result = mock.perform(multipart("/file/submit").file(file2)
				.params(params)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andDo(print());


		//then stating what is expected as a response from the servlet
		result.andExpect(status().isOk());
		result.andExpect(mvcResult -> Assert.assertTrue("The file did contain content",file1.getSize() > 0));
	}


	@Test
	public void givenEmpty_GivenNoSuchFile_WhenFileUpload_shouldReturnError() throws Exception {

			//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
			params.add("file", file3.getBytes().toString());
			params.add("name", json.toString());

			//this is mocking the servlet being called and being passed the needed parameters for desired services
			final ResultActions result = mock.perform(multipart("/file/submit").file(file3)
					.params(params)
					.accept(MediaType.MULTIPART_FORM_DATA_VALUE))
					.andDo(print());

			//then stating what is expected as a response from the servlet
			result.andExpect(status().is4xxClientError());
	}

	@Test
	public void givenFormData_whenFileUpload_theReturnErrorForWrongFiletype() throws Exception {
		//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
		params.add("file", file4.getBytes().toString());
		params.add("name", json.toString());

		//this is mocking the servlet being called and being passed the needed parameters for desired services
		final ResultActions result = mock.perform(multipart("/file/submit").file(file4)
				.params(params)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(content().string("Unsupported file type. Please upload either a video or a text file."))
				.andDo(print());

		//then stating what is expected as a response from the servlet
		result.andExpect(content().string("Unsupported file type. Please upload either a video or a text file."));
	}
}
