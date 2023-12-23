package estudo.java.springboot.screensoundmusic.screensoundmusic;

import estudo.java.springboot.screensoundmusic.screensoundmusic.principal.Principal;
import estudo.java.springboot.screensoundmusic.screensoundmusic.repository.iArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreensoundmusicApplication implements CommandLineRunner {

	@Autowired
	private iArtistaRepository artistaRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreensoundmusicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(artistaRepository);
		principal.exibirMenu();
	}

}
