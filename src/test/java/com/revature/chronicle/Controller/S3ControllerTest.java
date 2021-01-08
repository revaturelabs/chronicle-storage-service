package com.revature.chronicle.Controller;




import com.revature.chronicle.services.InsertFileService;
import com.revature.chronicle.services.NoteService;
import com.revature.chronicle.services.S3FileService;
import com.revature.chronicle.services.VideoService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-application-context.xml"})
public class S3ControllerTest {

	//Mocker object
	private static MockMvc mock;

	private static MockMultipartFile file1;
	private static MockMultipartFile file2;
	private static JSONObject json;

	private static S3FileService s3FileMock;

	private static VideoService videoMock;
	private static NoteService noteMock;

	@BeforeClass
	public static void setup() throws JSONException {
		s3FileMock = mock(S3FileService.class);
		videoMock = mock(VideoService.class);
		noteMock = mock(NoteService.class);

		mock = MockMvcBuilders.standaloneSetup(new FileUploadController(s3FileMock, videoMock, noteMock)).build();

	 	json = new JSONObject();
	 	json.put("testing", "123");
		json.put("test2","here we go");

		//given a mock MultipartFile to pass into the controller
		file1 = new MockMultipartFile(
				"goodfile",
				"test.txt",
				MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes()
		);

		//given a mock MultipartFile to pass into the controller
		file2 = new MockMultipartFile(
				"badfile",
				"test.txt",
				MediaType.TEXT_PLAIN_VALUE,
				"".getBytes()
		);
	}


	@Test
	public void givenFormData_whenFileUpload_theReturnOKStatus() throws Exception {

		//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("file", file1.getBytes().toString());
		params.add("json", json.toString());


		//this is mocking the servlet being called and being passed the needed parameters for desired services
		final ResultActions result = mock.perform(multipart("/s3/upload")
				.params(params)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.characterEncoding("utf-8")
				.content(params.toString()));

		//then stating what is expected as a response from the servlet
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(mvcResult -> Assert.assertTrue("The file did contain content",file1.getSize() > 0));
	}


	@Test
	public void givenEmpty_GivenNoSuchFile_WhenFileUpload_shouldReturnError() throws Exception {

		//this multivalue map is mocking the passing of a file and json description as parameters into the servlet
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("file", file2.getBytes().toString());
		params.add("json", json.toString());

		//this is mocking the servlet being called and being passed the needed parameters for desired services
		final ResultActions result = mock.perform(multipart("/s3/upload")
				.params(params)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE)
				.characterEncoding("utf-8")
				.content(params.toString()));

		//then stating what is expected as a response from the servlet
		result.andDo(print());
		result.andExpect(status().is4xxClientError());
		result.andExpect(mvcResult -> Assert.assertTrue("The file given was empty or doesnt exist",file2.isEmpty()));
	}
}
