package br.com.emersonbarros;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boot {

	public static void main(String[] args) {
		try {
			SpringApplication.run(Boot.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
