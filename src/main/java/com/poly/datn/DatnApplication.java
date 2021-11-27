package com.poly.datn;


import com.poly.datn.config.FileStorageProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableSwagger2
public class DatnApplication {
    public static void main(String[] args){

        Environment env =  SpringApplication.run(DatnApplication.class, args).getEnvironment();
        String appName = env.getProperty("spring.application.name");
        if (appName != null) {
            appName = appName.toUpperCase();
        }

        String port = env.getProperty("server.port");
        log.info("-------------------------START " + appName
                + " Application------------------------------");
        log.info("   Application         : " + appName);
        log.info("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        log.info("-------------------------START SUCCESS " + appName
                + " Application------------------------------");

    }

}

