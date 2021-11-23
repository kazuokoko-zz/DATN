package com.poly.datn;


import com.poly.datn.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class DatnApplication {
    public static void main(String[] args){
        SpringApplication.run(DatnApplication.class, args);

    }

}

