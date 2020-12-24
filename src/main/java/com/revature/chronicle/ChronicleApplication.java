package com.revature.chronicle;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.daos.UserRepo;
import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@RestController
public class ChronicleApplication {

	@Autowired
	UserRepo userRepo;
	@Autowired
	TagRepo tagRepo;
	@Autowired
	VideoRepo videoRepo;

	public static void main(String[] args) {
		SpringApplication.run(ChronicleApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			User u = new User();
			u.setUsername("bob");
			userRepo.save(u);

			User u2 = new User();
			u2.setUsername("notbob");
			userRepo.save(u2);

			Tag t = new Tag();
			t.setName("Topic");
			t.setValue("Angular");


			Video v = new Video();
			v.setDescription("TEST");
			v.setUser(u);
			v.setUrl("http://blah.com");
			Set<Tag> tags = new HashSet<>();
			tags.add(t);
			v.setVideo_tags(tags);

			videoRepo.save(v);

			Optional<Video> video = videoRepo.findById(1);
			if(video.isPresent()){
				System.out.println(video.toString());
			}

			Optional<User> user = userRepo.findById(2);
			if(user.isPresent()){
				System.out.println("Here");
			}
			System.out.println(user.toString());
			List<User> users = userRepo.findAll();
			for(User us : users) {
				System.out.println(us.toString());
			}
		};
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
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