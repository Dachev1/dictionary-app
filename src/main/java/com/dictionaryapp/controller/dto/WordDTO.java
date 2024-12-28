package com.dictionaryapp.controller.dto;

import com.dictionaryapp.model.entity.enums.LanguageEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WordDTO implements Serializable {

    @NotBlank
    @Size(min = 2, max = 40)
    private String term;

    @NotBlank
    @Size(min = 2, max = 40)
    private String translation;

    @NotBlank
    @Size(min = 2, max = 40)
    private String example;

    @PastOrPresent
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate inputDate;

    @NotNull
    private LanguageEnum language;


    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public LocalDate getInputDate() {
        return inputDate;
    }

    public void setInputDate(LocalDate inputDate) {
        this.inputDate = inputDate;
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }
}
