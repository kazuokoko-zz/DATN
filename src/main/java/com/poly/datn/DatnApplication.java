package com.poly.datn;


import com.poly.datn.config.FileStorageProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableSwagger2
@EnableScheduling
public class DatnApplication {

    public static void main(String[] args) {


        Environment env = SpringApplication.run(DatnApplication.class, args).getEnvironment();

        String port = env.getProperty("server.port");
        log.info("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        log.info("-------------------------START SUCCESS------------------------------");

    }

}

