package com.catalog.controller;

import com.catalog.model.Course;
import com.catalog.model.ServiceResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Priyank Agrawal
 */
public interface CourseController {

    public ResponseEntity<ServiceResponse> addCourse(@Valid Course course);

    public ResponseEntity<ServiceResponse> listAllCourses();

    public ResponseEntity<ServiceResponse> deleteCourse(@Positive Long courseId);

    public ResponseEntity<ServiceResponse> updateCourse(@Valid Course course);

    public ResponseEntity<ServiceResponse> getCourseById(@Positive Long courseId);
}
