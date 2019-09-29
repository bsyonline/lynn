package com.rolex.lynn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author rolex
 * @Since 29/09/2019
 */
@SpringBootApplication
@ServletComponentScan
public class LynnApplication {
    public static void main(String[] args) {
        SpringApplication.run(LynnApplication.class, args);
    }
    
}
