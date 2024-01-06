package com.itcast.reggle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan // to scan the web filter
public class ReggleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggleApplication.class, args);
        log.info("Started!!!");
    }

}
