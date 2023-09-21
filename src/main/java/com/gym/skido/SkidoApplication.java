package com.gym.skido;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkidoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SkidoApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
