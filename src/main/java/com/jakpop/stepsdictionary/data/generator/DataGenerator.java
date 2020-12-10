package com.jakpop.stepsdictionary.data.generator;

import com.jakpop.stepsdictionary.data.entity.enums.Role;
import com.jakpop.stepsdictionary.data.entity.users.User;
import com.jakpop.stepsdictionary.data.service.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());

//            userRepository.save(new User("user", "u", Role.USER));
//            userRepository.save(new User("admin", "a", Role.ADMIN));

            logger.info("Generated demo data");
        };
    }

}
