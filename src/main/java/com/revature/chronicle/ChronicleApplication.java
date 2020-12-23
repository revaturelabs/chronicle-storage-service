package com.revature.chronicle;

import com.revature.chronicle.models.Tag;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@SpringBootApplication
@RestController
public class ChronicleApplication {

	private SessionFactory sessionFactory;

	public static void main(String[] args) {
		SpringApplication.run(ChronicleApplication.class, args);

		ChronicleApplication c = new  ChronicleApplication();
		c.configure();

		Tag t = new Tag();
		t.setName("Tag");
		System.out.println(t.toString());

	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	/**
	 * Testing H2
	 */
	private void configure() {
		Configuration config = new Configuration().configure();

		if(config != null) {
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
			this.sessionFactory = config.buildSessionFactory(builder.build());
		}
	}

}