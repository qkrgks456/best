package com.gudi.best;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@EnableScheduling
@SpringBootApplication
public class BestApplication {
    // @EnableScheduling 이거는 스케줄러 사용 사용할 클래스에 @Component
    public static void main(String[] args) {
        String APPLICATION_LOCATIONS = null;
        ClassPathResource resource = new ClassPathResource("aws.properties");
        if (resource.exists()) {
            APPLICATION_LOCATIONS = "spring.config.location="
                    + "classpath:aws.properties,classpath:application.properties";
        } else {
            APPLICATION_LOCATIONS = "spring.config.location=classpath:application.properties";
        }
        new SpringApplicationBuilder(BestApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
