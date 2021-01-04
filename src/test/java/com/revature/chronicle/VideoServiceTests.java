package com.revature.chronicle;

import com.revature.chronicle.daos.UserRepo;
import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.TagService;
import com.revature.chronicle.services.UserService;
import com.revature.chronicle.services.VideoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@Component
public class VideoServiceTests {
    @Mock
    private VideoRepo repo;

    @InjectMocks
    private VideoService service;

    private Tag tag1 = new Tag();
    private Tag tag2 = new Tag();
    private Tag tag3 = new Tag();
    private User user = new User();
    private Video video1 = new Video();
    private Video video2 = new Video();

    @BeforeEach
    public void setup(){
        System.out.println("mocking");
        MockitoAnnotations.openMocks(this);

        user.setUsername("TESTUSER");

        tag1.setName("Technology");
        tag1.setValue("Angular");


        tag2.setName("Technology");
        tag2.setValue("Java");

        tag3.setName("Batch");
        tag3.setValue("1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);
        video1.setUrl("http://video1.com");
        video1.setUser(user);
        video1.setDescription("A description");
        video1.setVideoTags(tags1);
        System.out.println(video1.toString());

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);
        video2.setUrl("http://video2.com");
        video2.setUser(user);
        video2.setDescription("A description");
        video2.setVideoTags(tags2);

    }

    @Test
    public void getAllVideos(){
        when(service.findAll()).thenReturn(
                new ArrayList<Video>(Arrays.asList(video1,video2))
        );

        List<Video> result = service.findAll();
        for(Video v:result){
            System.out.println(v.toString());
        }
        Assert.assertTrue(result.size()>0);
    }
    @Test
    public void shouldReturnAllVideoWithTag(){
        List<Tag> findTags = new ArrayList();
        findTags.add(tag1);
        findTags.add(tag2);
        List<Video> result = service.findAllVideosByTags(findTags);
        for(Video v:result){
            System.out.println(v.toString());
        }
    }
}
