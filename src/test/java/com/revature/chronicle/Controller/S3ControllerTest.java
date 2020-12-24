package com.revature.chronicle.Controller;


import akka.http.javadsl.model.Multipart;
import com.amazonaws.services.workdocs.model.ActivateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.revature.chronicle.Service.S3Service;
import org.json.JSONObject;
import org.junit.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import scala.reflect.internal.util.NoFile;
import scala.util.parsing.json.JSON;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:test_application.properties"})
@WebMvcTest(S3Controller.class)
public class S3ControllerTest {

	//Mocker object
	@Autowired
	private MockMvc mvc;


	private JSONObject json;
	private MultipartFile multi;


	//Mock services
	@MockBean
	private S3Service service;


	//setup methods
	@Before
	public void setup() {

//	multi = new MockMultipartFile("request","request", MediaType.APPLICATION_JSON_VALUE, "hello".getBytes());
//
//
//	json =  { "expiration": "2015-12-30T12:00:00.000Z",
//				"conditions": [
//		{"bucket": "sigv4examplebucket"},
//		["starts-with", "$key", "user/user1/"],
//		{"acl": "public-read"},
//		{"success_action_redirect": "http://sigv4examplebucket.s3.amazonaws.com/successful_upload.html"},
//		["starts-with", "$Content-Type", "image/"],
//		{"x-amz-meta-uuid": "14365123651274"},
//		{"x-amz-server-side-encryption": "AES256"},
//	    ["starts-with", "$x-amz-meta-tag", ""],
//
//		{"x-amz-credential": "AKIAIOSFODNN7EXAMPLE/20151229/us-east-1/s3/aws4_request"},
//		{"x-amz-algorithm": "AWS4-HMAC-SHA256"},
//		{"x-amz-date": "20151229T000000Z" }
//		]
//		}

	}



	@Test
	public void givenFormData_whenFileUpload_theReturnOK() throws Exception {
		when(service.uploadFileS3(MediaType.MULTIPART_FORM_DATA_VALUE))
				.thenReturn(anyString());

		mvc.perform(post("/s3/upload")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.content(anyString()))
				.andExpect(status().isOk());

	}


	@Test
	public void givenFormData_WhenFileUpload_shouldReturnError() throws Exception {

		when(service.uploadFileS3(MediaType.MULTIPART_FORM_DATA_VALUE))
			.thenReturn(anyString());

		mvc.perform(post("/s3/upload")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.content(anyString()))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void givenFileToUpload_WhenFileUpload_shouldReturnOk() throws Exception{

	}

}
