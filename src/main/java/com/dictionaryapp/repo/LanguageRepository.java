package com.dictionaryapp.repo;

import com.dictionaryapp.model.entity.Language;
import com.dictionaryapp.model.entity.enums.LanguageEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByLanguageName(LanguageEnum languageName);
}
