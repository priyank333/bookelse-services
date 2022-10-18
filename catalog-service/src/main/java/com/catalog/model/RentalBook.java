/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class RentalBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long rentalBookId;

    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String title;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String shortTitle;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subtitle;

    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String author;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String editor;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String translator;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reviewer;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String illustrator;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contributor;

    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String publisher;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publisherCity;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate publicationDate;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isbn;

    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String language;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tagLine;

    @Column(nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String shortDescription;

    @Column(nullable = true)
    @Lob
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String longDescription;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 100)
    private Double depreciation;

    public Long getRentalBookId() {
        return rentalBookId;
    }

    public void setRentalBookId(Long rentalBookId) {
        this.rentalBookId = rentalBookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherCity() {
        return publisherCity;
    }

    public void setPublisherCity(String publisherCity) {
        this.publisherCity = publisherCity;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Double depreciation) {
        this.depreciation = depreciation;
    }

    @Override
    public String toString() {
        return "RentalBook{" + "rentalBookId=" + rentalBookId + ", title=" + title + ", shortTitle=" + shortTitle
                + ", subtitle=" + subtitle + ", author=" + author + ", editor=" + editor + ", translator=" + translator
                + ", reviewer=" + reviewer + ", illustrator=" + illustrator + ", contributor=" + contributor
                + ", publisher=" + publisher + ", publisherCity=" + publisherCity + ", publicationDate="
                + publicationDate + ", isbn=" + isbn + ", language=" + language + ", tagLine=" + tagLine
                + ", shortDescription=" + shortDescription + ", longDescription=" + longDescription + ", depreciation="
                + depreciation + '}';
    }

}
