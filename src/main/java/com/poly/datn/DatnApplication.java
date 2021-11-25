package com.poly.datn;


import com.poly.datn.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableSwagger2
public class DatnApplication {
    public static void main(String[] args){
        SpringApplication.run(DatnApplication.class, args);

    }

}

