package com.revature.chronicle;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.daos.UserRepo;
import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//@DataJpaTest
@SpringBootTest
@Component
public class VideoRepoTest {
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TagRepo tagRepo;

    User user1 = new User();
    Tag tag1 = new Tag();
    Tag tag2 = new Tag();
    Tag tag3 = new Tag();

    @BeforeEach
    void setUp() {
        //set up the user
        user1.setUserID(1);
        user1.setUsername("BillyBob");
        //save the user into our h2 db
        userRepo.save(user1);
        //set up the tags
        tag1.setTagID(1);
        tag1.setName("Technology");
        tag1.setValue("Java");

        tag2.setTagID(2);
        tag2.setName("Technology");
        tag2.setValue("Angular");

        tag3.setTagID(3);
        tag3.setName("Technology");
        tag3.setValue("Spring");
        //save the tags into the our h2 db
        tagRepo.save(tag1);
        tagRepo.save(tag2);
        tagRepo.save(tag3);
        //place the tags in the set
        Set<Tag> tag_set1 = (Set<Tag>) tagRepo.findAll(); //cast from list to ser (suppress the warning)
        //build the videos using user1 and tag_set1
        Video video1 = Video.builder()
                .url("video1.com")
                .description("This is video 1")
                .user(user1) //ManyToOne: many videos can be uploaded by one user
                .video_tags(tag_set1) //ManyToMany: Many videos can share the same (Many) tags
                .build();
        Video video2 = Video.builder()
                .url("video2.com")
                .description("This is video 2")
                .user(user1) //ManyToOne: many videos can be uploaded by one user
                .video_tags(tag_set1) //ManyToMany: Many videos can share the same (Many) tags
                .build();
        //save the videos into our h2 db
        videoRepo.save(video1);
        videoRepo.save(video2);
    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
        tagRepo.deleteAll();
        videoRepo.deleteAll();
    }

//    @Test
//    void findVideosByTagTest() {
//        //List<Video> result = videoService.findVideosByTagService(tag1);//find all videos containing this tag
//        List<Video> result = videoRepo.findVideosByTag(tag1); //find all videos containing this tag
//        assertThat(result, is(notNullValue())); //use an assert that returns a message maybe
//    }
//
//    @Test
//    void findVideosByTagNull() {
//        Tag tag4 = new Tag();
//        tag4.setTagID(4);
//        tag4.setName("Technology");
//        tag4.setValue("TestTag");
//        List<Video> result = videoRepo.findVideosByTag(tag4); //find all videos containing this tag
//        assertThat(result, is(nullValue())); //there should be no videos with this tag
//    }
}



    //Old Dao Method tests (Probably not useful anymore)
    /*
    @Test
    public void getVideosTest() {
        List<Video> list = videoRepo.getVideos();
        Assert.notNull(list, "assertion failed: list returned null");
    }

    @Test
    public void getVideoByIdTest(){
        Video video = new Video();
        int vidId = 1;
        String vidUrl = "vid.Url";
        String vidDesc = "video description";
        video.setVideoID(vidId);
        video.setUrl(vidUrl);
        video.setDescription(vidDesc);
        //video.setUser(); //how do I set/mock a user?
        //video.setVideo_tags();//do I set/mock the video_tags?
        videoDAO.addVideo(video);
        //if I'm just testing getVideoById do I need to make sure every other field in video is set?
        Assert.notNull(video, "assertion failed: getVideoById returned null"); //if the assertion fails throw a message
    }

    @Test
    public void getVideoByIdTest(){
        Video video = videoRepo.getVideoById(1);
        Assert.notNull(video, "assertion failed: getVideoById returned null");
    }

    @Test
    public void getVideoByIdTestNull(){ //should I do null tests? is there a point?
        Video video = videoRepo.getVideoById(-1);
        Assert.isNull(video, "assertion failed: getVideoById returned not-null");
    }

    @Test
    public void addVideoTest(){
        Video video = new Video(); //should I set the model fields
        boolean addTest = videoRepo.addVideo(video);
        Assert.isTrue(addTest,"assertion failed: addTest is false");
    }

    @Test
    public void updateVideoTest(){
        Video video = new Video(); //should I set the model fields
        boolean updateTest = videoRepo.updateVideo(video);
        Assert.isTrue(updateTest,"assertions failed: updateVideo is false");
    }

    @Test
    public void deleteVideoTest(){
        boolean deleteTest = videoRepo.deleteVideoById(1);
        Assert.isTrue(deleteTest,"assertions failed: deleteVideo is false");
    }
    */