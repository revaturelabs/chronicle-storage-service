package com.revature.chronicle.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// S3 to database service tests
// takes two strings as input
// url and json object

@SpringBootTest
public class InsertFileTests {
    // 2nd object will have:
    // id, s3_url, title, created_by, created_on, and
    // more (tags for sure, possibly even more)
    // all in  json


    //private
    JSONObject upload;
    ObjectMapper om;


    InsertFileService ifsMock = Mockito.mock(InsertFileService.class);



    @Before
    public void setupForInsertFileTests(){
        // create file model (or video model)
        // use object mapper to write it into string (so it is json)
        // use json object to pass into tests
        // url too
        JSONObject upload = new JSONObject();
        om = new ObjectMapper();
    }

    // addSuccess is used in these tests. would be a boolean method that
    // checks a boolean data member of the class
    @Test
    void canInsertFileIntoDB (String url, Object formInfo) throws JsonProcessingException {
        // need a valid model for a db entry

        String processedForm = om.writeValueAsString(formInfo);
        Mockito.when(ifsMock.insertFile(url, processedForm));
//                .thenReturn(true);
        assertEquals(true, ifsMock.addSuccess());
    }

    @Test
    void canFailInsertionBadURL(String url, Object formInfo) throws JsonProcessingException {
        // needs a bad url to trigger the catch in the method that
        // adds to the db
        String badUrl = "";
        String processedForm = om.writeValueAsString(formInfo);
        Mockito.when(ifsMock.insertFile(badUrl, processedForm));
//                .thenReturn(false);
        assertEquals(false, ifsMock.addSuccess());
    }

    @Test
    void canFailInsertionBadFormData(String url, Object formInfo) {
        // needs a bad set of form information to trigger the catch in the method
        // that adds to the db
        String badFormInfo = "";
        Mockito.when(ifsMock.insertFile(url, badFormInfo));
//                .thenReturn(false);
        assertEquals(false, ifsMock.addSuccess());
    }
}
