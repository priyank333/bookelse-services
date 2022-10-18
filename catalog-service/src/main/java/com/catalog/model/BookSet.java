/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author Priyank Agrawal
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class BookSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(updatable = false)
    private Long setId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name = "universityId", nullable = false)
    private University university;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "setId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty
    private Set<BookSetItem> books;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "BookSetToCourseMapper", joinColumns = @JoinColumn(name = "setId", unique = false), inverseJoinColumns = @JoinColumn(name = "courseId", unique = false))
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = true)
    private List<Course> courses;
    @Column(nullable = false)
    @Positive
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer semester;
    // @JsonInclude(JsonInclude.Include.NON_NULL)
    // @Column(nullable = false)
    // @Min(value = 0)
    // @Max(value = 100)
    // private Double depreciation;

    public BookSet() {
    }

    public BookSet(Long setId) {
        this.setId = setId;
    }

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Set<BookSetItem> getBooks() {
        return books;
    }

    public void setBooks(Set<BookSetItem> books) {
        this.books = books;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "BookSet{" + "setId=" + setId + ", university=" + university + ", books=" + books + ", courses="
                + courses + ", semester=" + semester + '}';
    }

}
