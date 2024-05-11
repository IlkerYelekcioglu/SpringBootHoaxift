package com.hoaxify.ws;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner userCreater(UserRepository userRepository){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return  (args) -> {
       for(var i= 1;i<=25;i++){
				 User user = new User();
				 user.setUsername("user"+i);
				 user.setEmail("user"+i+"@mail.com");
				 user.setPassword(passwordEncoder.encode("P4ssword4"));
				 user.setActive(true);
				 userRepository.save(user);
			 }

		};
	}
	/**
	 * Görevleri, Boot uygulaması tamamen başladığı andan sonra bir takım kodların çalıştırılmasını sağlar. İnterface’te tanımlı olan run() metodu çağıırılır.
	 * Profile ise hangi modda çalıştıracağımıza karar veririz.
	 */
}
