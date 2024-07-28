package com.hoaxify.ws;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner userCreater(UserRepository userRepository,PasswordEncoder passwordEncoder){
    return  (args) -> {
			var userInDB = userRepository.findByEmail("user1@mail.com");
			if(userInDB != null) return;
       for(var i= 1;i<=25;i++){
				 User user = new User();
				 user.setUsername("user"+i);
				 user.setEmail("user"+i+"@mail.com");
				 user.setPassword(passwordEncoder.encode("P4ssword4"));
				 user.setActive(i != 1);
				 userRepository.save(user);
			 }
		};
	}
	/**
	 * Görevleri, Boot uygulaması tamamen başladığı andan sonra bir takım kodların çalıştırılmasını sağlar. İnterface’te tanımlı olan run() metodu çağıırılır.
	 * Profile ise hangi modda çalıştıracağımıza karar veririz.
	 */
}
