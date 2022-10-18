package com.catalog.controller.impl;

import com.catalog.model.Course;
import com.catalog.model.ServiceResponse;
import com.catalog.controller.mgr.CourseControllerMgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.catalog.controller.CourseController;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Priyank Agrawal
 */
@RestController
@RequestMapping("/course")
@Validated
public class CourseControllerImpl implements CourseController {

    @Autowired
    private CourseControllerMgr courseServiceMgr;

    @PostMapping("/v1/add-course")
    @Override
    public ResponseEntity<ServiceResponse> addCourse(@Valid @RequestBody Course course) {
        ServiceResponse serviceResponse = courseServiceMgr.addCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
    }

    @GetMapping("/v1/list-course")
    @Override
    public ResponseEntity<ServiceResponse> listAllCourses() {
        ServiceResponse serviceResponse = courseServiceMgr.listAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
    }

    @GetMapping("/v1/list-course/{courseId}")
    @Override
    public ResponseEntity<ServiceResponse> getCourseById(@PathVariable("courseId") @Positive Long courseId) {
        ServiceResponse serviceResponse = courseServiceMgr.getCourseById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
    }

    @DeleteMapping("/v1/delete-course")
    @Override
    public ResponseEntity<ServiceResponse> deleteCourse(@Positive @RequestParam Long courseId) {
        ServiceResponse serviceResponse = courseServiceMgr.deleteCourse(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
    }

    @PutMapping("/v1/update-course")
    @Override
    public ResponseEntity<ServiceResponse> updateCourse(@Valid @RequestBody Course course) {
        ServiceResponse serviceResponse = courseServiceMgr.updateCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
    }

}
