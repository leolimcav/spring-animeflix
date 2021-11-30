package br.ufc.quixada.alu.leonardo.animeflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class AnimeflixApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnimeflixApplication.class, args);
	}

}
