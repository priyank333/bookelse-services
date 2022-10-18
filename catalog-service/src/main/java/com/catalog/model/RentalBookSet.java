/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class RentalBookSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long rentalSetId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name = "universityId", nullable = false)
    private University university;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "rentalSetId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty
    private Set<RentalBookSetItem> rentalBooks;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "RentalBookSetToCourseMapper", joinColumns = @JoinColumn(name = "rentalSetId", unique = false), inverseJoinColumns = @JoinColumn(name = "courseId", unique = false))
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = true)
    private List<Course> courses;
    @Column(nullable = false)
    @Positive
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer semester;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 100)
    private Double depreciation;

    public Long getRentalSetId() {
        return rentalSetId;
    }

    public void setRentalSetId(Long rentalSetId) {
        this.rentalSetId = rentalSetId;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Set<RentalBookSetItem> getRentalBooks() {
        return rentalBooks;
    }

    public void setRentalBooks(Set<RentalBookSetItem> rentalBooks) {
        this.rentalBooks = rentalBooks;
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

    public Double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Double depreciation) {
        this.depreciation = depreciation;
    }

    @Override
    public String toString() {
        return "RentalBookSet{" + "rentalSetId=" + rentalSetId + ", university=" + university + ", rentalBooks="
                + rentalBooks + ", courses=" + courses + ", semester=" + semester + ", depreciation=" + depreciation
                + '}';
    }

}
