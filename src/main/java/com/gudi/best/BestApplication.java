package com.gudi.best;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BestApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:aws.properties,classpath:application.properties";

    // @EnableScheduling 이거는 스케줄러 사용
    public static void main(String[] args) {
        new SpringApplicationBuilder(BestApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
