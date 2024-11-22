package com.example.clinicmanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Clinic Manager.
 */
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Clinic Manager API",
                version = "1.0",
                description = "API documentation for the Clinic Manager application",
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        ),
        servers = @Server(url = "/", description = "Default Server URL")
)
public class ClinicManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicManagerApplication.class, args);
    }
}
