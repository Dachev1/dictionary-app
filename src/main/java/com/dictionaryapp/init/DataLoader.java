package com.dictionaryapp.init;

import com.dictionaryapp.model.entity.Language;
import com.dictionaryapp.model.entity.enums.LanguageEnum;
import com.dictionaryapp.repo.LanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner preloadLanguages(LanguageRepository languageRepository) {
        return args -> {
            if (languageRepository.count() == 0) {
                for (LanguageEnum lang : LanguageEnum.values()) {
                    languageRepository.save(new Language(lang));
                }
                logger.info("Languages initialized in the database.");
            } else {
                logger.info("Languages already exist in the database. Initialization skipped.");
            }
        };
    }
}
