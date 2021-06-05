package com.majority.mynotebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyNotebookApplication {

	public static void main(String[] args) {
//		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//
//		System.out.println(bcrypt.encode("123456"));
		SpringApplication.run(MyNotebookApplication.class, args);
	}

}
