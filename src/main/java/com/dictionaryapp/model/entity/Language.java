package com.dictionaryapp.model.entity;

import com.dictionaryapp.model.entity.enums.LanguageEnum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private LanguageEnum languageName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "language")
    private Set<Word> words;

    public Language() {
        this.words = new HashSet<>();
    }

    public Language(LanguageEnum languageName) {
        super();

        this.languageName = languageName;
        this.description = languageName.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LanguageEnum getLanguageName() {
        return languageName;
    }

    public void setLanguageName(LanguageEnum languageName) {
        this.languageName = languageName;
        this.description = languageName.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }
}
