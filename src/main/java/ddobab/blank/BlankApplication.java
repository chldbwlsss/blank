package ddobab.blank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlankApplication.class, args);
	}

}
