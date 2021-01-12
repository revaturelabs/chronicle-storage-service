package com.revature.chronicle;

import com.revature.chronicle.Controller.VideoController;
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
import com.revature.chronicle.security.CorsConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.RowSet;
import java.util.*;


@SpringBootApplication
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
			user.setUsername("August Duet");
			userService.save(user);

			Tag tag1 = new Tag();
			tag1.setName("Technology");
			tag1.setValue("Angular");
			tagService.save(tag1);

			Tag tag2 = new Tag();
			tag2.setName("Technology");
			tag2.setValue("Spring");
			tagService.save(tag2);

			Tag tag3 = new Tag();
			tag3.setName("Batch");
			tag3.setValue("1120-August");
			tagService.save(tag3);

			Tag tag4 = new Tag();
			tag4.setName("Title");
			tag4.setValue("Angular and TypeScript Overview");
			tagService.save(tag4);

			Tag tag5 = new Tag();
			tag5.setName("Title");
			tag5.setValue("Spring Boot Overview and Setup");
			tagService.save(tag5);

			Tag tag6 = new Tag();
			tag6.setName("Date");
			tag6.setValue("11/21/2020");
			tagService.save(tag6);

			Tag tag7 = new Tag();
			tag7.setName("Date");
			tag7.setValue("11/25/2020");
			tagService.save(tag7);

			Set<Tag> tags1 = new HashSet<>();
			tags1.add(tag1);
			tags1.add(tag3);
			tags1.add(tag4);
			tags1.add(tag6);
			Video video1 = new Video();
			video1.setUrl("https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4");
			video1.setUser(user);
			video1.setDescription("Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.");
			video1.setVideoTags(tags1);
			videoService.save(video1);

			Set<Tag> tags2 = new HashSet<>();
			tags2.add(tag2);
			tags2.add(tag3);
			tags2.add(tag5);
			tags2.add(tag7);
			Video video2 = new Video();
			video2.setUrl("https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4");
			video2.setUser(user);
			video2.setDescription("Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods");
			video2.setVideoTags(tags2);
			videoService.save(video2);

			Note note1 = new Note();
			note1.setUrl("http://www.africau.edu/images/default/sample.pdf");
			note1.setUser(user);
			note1.setDescription("Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.");
			note1.setNoteTags(tags1);
			noteService.save(note1);

			Note note2 = new Note();
			note2.setUrl("https://www.w3.org/TR/PNG/iso_8859-1.txt");
			note2.setUser(user);
			note2.setDescription("Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods");
			note2.setNoteTags(tags2);
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

}
