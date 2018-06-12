package com.logon.pet.petlogonweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication
@EntityScan("com.logon.pet.petlogoncore.domain")
@EnableJpaRepositories("com.logon.pet.petlogoncore.Repository")
@ComponentScan(basePackages = {"com.logon.pet.petlogonweb", "com.logon.pet.petlogoncore"})
public class PetLogonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetLogonWebApplication.class, args);
    }
}