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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

@SpringBootTest
public class VideoServiceTests {
    @Mock
    private VideoRepo repo;

    @InjectMocks
    private VideoService service;


    @Test
    public void shouldReturnAListOfAllVideos(){
        //Video video = new Video(1,"www.video.com","a title","A test video",new Date(),"",new ArrayList<Tag>(), 0);
        Video video = new Video("A test video",new Date(),"",new ArrayList<Tag>(),false);
        when(repo.findAll()).thenReturn(
                new ArrayList<Video>(Arrays.asList(video))
        );

        List<Video> result = service.findAll();
        Assert.assertTrue(result.contains(video));
        verify(repo).findAll();
    }

    @Test
    public void shouldReturnAVideoById(){
        //Video video = new Video(6, "www.video.com","a title", "A test video", new Date(), "", new ArrayList<Tag>(), 0);
        Video video = new Video("A test video",new Date(),"",new ArrayList<Tag>(),false);
        when(repo.findById(6)).thenReturn(
                Optional.of(video)
        );
        Optional<Video> result = Optional.ofNullable(service.findById(6));
        Assert.assertTrue(result.isPresent());
        if (result.isPresent()) {
            Assert.assertEquals(video, result.get());
        }
        verify(repo).findById(6);
    }

    @Test
    public void shouldReturnNullIfNoVideoFound(){
        when(repo.findById(67)).thenReturn(Optional.empty());
        Optional<Video> result = Optional.ofNullable(service.findById(67));
        Assert.assertFalse(result.isPresent());
        verify(repo).findById(67);
    }

    @Test
    public void shouldSaveAVideoAndReturnTrue(){
        Video video = new Video();
        video.setUrl("www.video.com");
        video.setUser("");
        video.setDescription("A new test video");
        video.setTags(new ArrayList<Tag>());
        when(repo.save(video)).thenReturn(video);
        boolean result = service.save(video);
        Assert.assertTrue(result);
        verify(repo).save(video);
    }

    @Test
    public void shouldFailToAddVideoAndReturnFalse(){
        Video video = new Video();
        video.setUser("");
        video.setDescription("A new test video");
        video.setTags(new ArrayList<>());
        when(repo.save(video)).thenThrow(IllegalArgumentException.class);
        boolean result = service.save(video);
        Assert.assertFalse(result);
        verify(repo).save(video);
    }

    @Test
    public void shouldReturnOnlyVideosThatMatchAllGivenTags(){

        Tag tag1 = new Tag("Technology","Angular");
        Tag tag2 = new Tag("Technology","Java");
        Tag tag3 = new Tag("Batch","1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);

        //Video video1 = new Video(1,"http://video1.com","a title","A description",new Date(),"",tags1, 0);
        //Video video2 = new Video(2,"http://video2.com","a title","A description",new Date(),"",tags2, 0);
        Video video1 = new Video("A description 1",new Date(),"",tags1,false);
        Video video2 = new Video("A description 2",new Date(),"",tags2,false);

        video1.setTags(tags1);
        video2.setTags(tags2);
        
       when(repo.findVideosWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Video>(Arrays.asList(video1,video2)));
        List<Video> result = service.findAllVideosByTags(Arrays.asList(tag1,tag3));
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(video1) && !result.contains(video2));
        verify(repo).findVideosWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfNoTagsAreFound(){
        Tag tag1 = new Tag("Technology","Angular");
        Tag tag2 = new Tag("Technology","Java");
        Tag tag3 = new Tag("Batch","1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);

        //Video video1 = new Video(1,"http://video1.com","a title","A description",new Date(),"",tags1, 0);
        //Video video2 = new Video(2,"http://video2.com","a title","A description",new Date(),"",tags2, 0);
        Video video1 = new Video("A description 1",new Date(),"",tags1,false);
        Video video2 = new Video("A description 2",new Date(),"",tags2,false);

        video1.setTags(tags1);
        video2.setTags(tags2);
        
        when(repo.findVideosWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Video>(Arrays.asList(video1,video2)));
        List<Video> result = service.findAllVideosByTags(Arrays.asList(tag2,tag3));
        Assert.assertTrue(result.isEmpty());
        verify(repo).findVideosWithOffsetAndLimit(0,50);
    }

    @Test
    public void shouldReturnAnEmptyListIfTagsAreEmpty(){
        Tag tag1 = new Tag("Technology","Angular");
        Tag tag2 = new Tag("Technology","Java");
        Tag tag3 = new Tag("Batch","1120-August");

        List<Tag> tags1 = new ArrayList<>();
        tags1.add(tag1);
        tags1.add(tag3);

        List<Tag> tags2 = new ArrayList<>();
        tags2.add(tag1);
        tags2.add(tag2);

        //Video video1 = new Video(1,"http://video.com","a title","A description",new Date(),"",tags1, 0);
        //Video video2 = new Video(2,"http://video.com","a title","A description",new Date(),"",tags2, 0);
        Video video1 = new Video("A description 1",new Date(),"",tags1,false);
        Video video2 = new Video("A description 2",new Date(),"",tags2,false);

        when(repo.findVideosWithOffsetAndLimit(0,50)).thenReturn(new ArrayList<Video>());
        List<Video> result = service.findAllVideosByTags(new ArrayList<Tag>());
        Assert.assertTrue(result.isEmpty());
        verify(repo).findVideosWithOffsetAndLimit(0,50);
    }
}
