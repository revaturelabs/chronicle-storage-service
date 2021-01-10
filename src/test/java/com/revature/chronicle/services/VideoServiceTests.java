package com.revature.chronicle.services;

import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.services.VideoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class VideoServiceTests {
    @Mock
    private VideoRepo repo;

    @InjectMocks
    private VideoService service;


    @Test
    public void shouldReturnAListOfAllVideos(){
        Video video = new Video(1,"www.video.com","A test video",new Date(),new User(),new HashSet<Tag>());
        when(repo.findAll()).thenReturn(
                new ArrayList<Video>(Arrays.asList(video))
        );

        List<Video> result = service.findAll();
        Assert.assertTrue(result.contains(video));
        verify(repo).findAll();
    }

    @Test
    public void shouldReturnAVideoById(){
        Video video = new Video(6, "www.video.com", "A test video", new Date(), new User(), new HashSet<Tag>());
        when(repo.findById(6)).thenReturn(
                Optional.of(video)
        );
        Optional<Video> result = service.findById(6);
        Assert.assertTrue(result.isPresent());
        if (result.isPresent()) {
            Assert.assertEquals(video, result.get());
        }
        verify(repo).findById(6);
    }

    @Test
    public void shouldReturnNullIfNoVideoFound(){
        when(repo.findById(67)).thenReturn(Optional.empty());
        Optional<Video> result = service.findById(67);
        Assert.assertFalse(result.isPresent());
        verify(repo).findById(67);
    }

    @Test
    public void shouldSaveAVideoAndReturnTrue(){
        Video video = new Video();
        video.setUrl("www.video.com");
        video.setUser(new User());
        video.setDescription("A new test video");
        video.setVideoTags(new HashSet<Tag>());
        when(repo.save(video)).thenReturn(video);
        boolean result = service.save(video);
        Assert.assertTrue(result);
        verify(repo).save(video);
    }

    @Test
    public void shouldFailToAddVideoAndReturnFalse(){
        Video video = new Video();
        video.setUser(new User());
        video.setDescription("A new test video");
        video.setVideoTags(new HashSet<Tag>());
        when(repo.save(video)).thenThrow(IllegalArgumentException.class);
        boolean result = service.save(video);
        Assert.assertFalse(result);
        verify(repo).save(video);
    }

    @Test
    public void shouldReturnOnlyVideosThatMatchAllGivenTags(){

        Tag tag1 = new Tag(1,"Technology","Angular");
        Tag tag2 = new Tag(2,"Technology","Java");
        Tag tag3 = new Tag(3,"Batch","1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Video video1 = new Video(1,"http://video1.com","A description",new Date(),new User(),tags1);
        Video video2 = new Video(2,"http://video2.com","A description",new Date(),new User(),tags2);

        when(repo.findVideosWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Video>(Arrays.asList(video1,video2)));
        List<Video> result = service.findAllVideosByTags(Arrays.asList(tag1,tag3));
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(video1) && !result.contains(video2));
        verify(repo).findVideosWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfNoTagsAreFound(){
        Tag tag1 = new Tag(1,"Technology","Angular");
        Tag tag2 = new Tag(2,"Technology","Java");
        Tag tag3 = new Tag(3,"Batch","1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Video video1 = new Video(1,"http://video1.com","A description",new Date(),new User(),tags1);
        Video video2 = new Video(2,"http://video2.com","A description",new Date(),new User(),tags2);

        when(repo.findVideosWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Video>(Arrays.asList(video1,video2)));
        List<Video> result = service.findAllVideosByTags(Arrays.asList(tag2,tag3));
        Assert.assertTrue(result.isEmpty());
        verify(repo).findVideosWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfTagsAreEmpty(){
        Tag tag1 = new Tag(1,"Technology","Angular");
        Tag tag2 = new Tag(2,"Technology","Java");
        Tag tag3 = new Tag(3,"Batch","1120-August");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        tags1.add(tag3);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);

        Video video1 = new Video(1,"http://video.com","A description",new Date(),new User(),tags1);
        Video video2 = new Video(2,"http://video.com","A description",new Date(),new User(),tags2);

        when(repo.findVideosWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Video>());
        List<Video> result = service.findAllVideosByTags(new ArrayList<Tag>());
        Assert.assertTrue(result.isEmpty());
        verify(repo).findVideosWithOffsetAndLimit(0,50);
    }
}
