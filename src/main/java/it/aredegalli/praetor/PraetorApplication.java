package it.aredegalli.praetor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"it.aredegalli.praetor"})
public class PraetorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraetorApplication.class, args);
    }

}
