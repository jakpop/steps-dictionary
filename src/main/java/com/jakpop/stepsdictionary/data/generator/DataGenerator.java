package com.jakpop.stepsdictionary.data.generator;

import com.jakpop.stepsdictionary.data.service.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
@Slf4j
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository) {
        return args -> {
//            log.info("Generated demo data");
        };
    }

}
