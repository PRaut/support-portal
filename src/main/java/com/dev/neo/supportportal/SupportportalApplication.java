package com.dev.neo.supportportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.dev.neo.supportportal.constant.FileConstant.*;

import java.io.File;
@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class}) /*Removes default whitelabel  spring error page*/
public class SupportportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupportportalApplication.class, args);
		System.out.println(USER_FOLDER);
		new File(USER_FOLDER).mkdirs();  
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
