package com.revature.chronicle;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.daos.UserRepo;
import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.NoteService;
import com.revature.chronicle.services.TagService;
import com.revature.chronicle.services.UserService;
import com.revature.chronicle.services.VideoService;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.RowSet;
import java.util.*;

@SpringBootApplication
@RestController
public class ChronicleApplication {
	@Autowired
	public UserService userService;
	@Autowired
	public TagService tagService;
	@Autowired
	public VideoService videoService;
	@Autowired
	public NoteService noteService;

	public static void main(String[] args) {
		SpringApplication.run(ChronicleApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			User user = new User();
			user.setUsername("TESTUSER");
			userService.save(user);

			Tag tag1 = new Tag();
			tag1.setName("Technology");
			tag1.setValue("Angular");
			tagService.save(tag1);

			Tag tag2 = new Tag();
			tag2.setName("Technology");
			tag2.setValue("Java");
			tagService.save(tag2);

			Tag tag3 = new Tag();
			tag3.setName("Batch");
			tag3.setValue("1120-August");
			tagService.save(tag3);

			Set<Tag> tags1 = new HashSet<>();
			tags1.add(tag1);
			tags1.add(tag3);
			Video video1 = new Video();
			video1.setUrl("http://video1.com");
			video1.setUser(user);
			video1.setDescription("A description");
			video1.setVideoTags(tags1);
			System.out.println(video1.toString());
			videoService.save(video1);

			Set<Tag> tags2 = new HashSet<>();
			tags2.add(tag1);
			tags2.add(tag2);
			Video video2 = new Video();
			video2.setUrl("http://video2.com");
			video2.setUser(user);
			video2.setDescription("A description");
			video2.setVideoTags(tags2);
			videoService.save(video2);

			Set<Tag> tags3 = new HashSet<>();
			tags3.add(tag1);
			tags3.add(tag3);
			Note note1 = new Note();
			note1.setUrl("http://video1.com");
			note1.setUser(user);
			note1.setDescription("A description");
			note1.setNoteTags(tags3);
			System.out.println(note1.toString());
			noteService.save(note1);

			Set<Tag> tags4 = new HashSet<>();
			tags4.add(tag1);
			tags4.add(tag2);
			Note note2 = new Note();
			note2.setUrl("http://video1.com");
			note2.setUser(user);
			note2.setDescription("A description");
			note2.setNoteTags(tags4);
			System.out.println(note2.toString());
			noteService.save(note2);


			List<Tag> input = new ArrayList<>();
			input.add(tag1);
			input.add(tag2);

			List<Video> result = videoService.findAllVideosByTags(input);
			for(Video r:result){
				System.out.println(r.toString());
			}

			List<Note> result2 = noteService.findAllNotesByTags(input);
			for(Note n:result2){
				System.out.println(n.toString());
			}
		};
	}


	/*
	private void configure() {
		Configuration config = new Configuration().configure();

		if(config != null) {
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
			this.sessionFactory = config.buildSessionFactory(builder.build());
		}
	}*/

}