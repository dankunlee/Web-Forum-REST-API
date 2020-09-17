package com.dankunlee.forumapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumApplication {
	public static void main(String[] args) {
		try {
			SpringApplication.run(ForumApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
