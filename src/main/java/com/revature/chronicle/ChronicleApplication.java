package com.revature.chronicle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.NoteService;
import com.revature.chronicle.services.TagService;

import com.revature.chronicle.services.VideoService;


@SpringBootApplication
public class ChronicleApplication extends SpringBootServletInitializer{
	@Autowired
	public TagService tagService;
	@Autowired
	public VideoService videoService;
	@Autowired
	public NoteService noteService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder){
        return springApplicationBuilder.sources(ChronicleApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(ChronicleApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			String user = "August Duet";

			Tag tag1 = new Tag();
			tag1.setType("Topic");
			tag1.setValue("Angular");
			tagService.save(tag1);

			Tag tag2 = new Tag();
			tag2.setType("Topic");
			tag2.setValue("Spring");
			tagService.save(tag2);

			Tag tag3 = new Tag();
			tag3.setType("Batch");
			tag3.setValue("1120-August");
			tagService.save(tag3);

			Tag tag4 = new Tag();
			tag4.setType("Batch");
			tag4.setValue("1020-Ben");
			tagService.save(tag4);

			Tag tag5 = new Tag();
			tag5.setType("Topic");
			tag5.setValue("Typescript");
			tagService.save(tag5);

			List<Tag> tags1 = new ArrayList<>();
			tags1.add(tag1);
			tags1.add(tag3);
			tags1.add(tag5);
			Video video1 = new Video();
			video1.setUrl("https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4");
			video1.setTitle("Angular and TypeScript Overview");
			video1.setUser(user);
			video1.setDescription("Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.");
			video1.setTags(tags1);
			videoService.save(video1);

			List<Tag> tags2 = new ArrayList<>();
			tags2.add(tag2);
			tags2.add(tag4);
			Video video2 = new Video();
			video2.setUrl("https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4");
			video2.setUser(user);
			video2.setTitle("Spring Boot Overview and Setup");
			video2.setDescription("Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods");
			video2.setTags(tags2);
			videoService.save(video2);

			Note note1 = new Note();
			note1.setUrl("http://www.africau.edu/images/default/sample.pdf");
			note1.setUser(user);
			note1.setTitle("Angular and TypeScript Overview");
			note1.setDescription("Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.");
			note1.setTags(tags1);
			noteService.save(note1);

			Note note2 = new Note();
			note2.setUrl("https://www.w3.org/TR/PNG/iso_8859-1.txt");
			note2.setUser(user);
			note2.setTitle("Spring Boot Overview and Setup");
			note2.setDescription("Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods");
			note2.setTags(tags2);
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
