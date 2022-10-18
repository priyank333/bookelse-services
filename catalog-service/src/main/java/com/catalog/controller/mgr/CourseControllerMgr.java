package com.catalog.controller.mgr;

import com.catalog.dao.CourseDao;
import com.catalog.model.Course;
import com.catalog.model.ServiceResponse;
import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class CourseControllerMgr {

    @Autowired
    private CourseDao courseDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse addCourse(Course course) {
        course = courseDao.save(course);
        LOGGER.info("Is course added : {}", course);
        final LinkedHashMap<String, Long> responseParam = new LinkedHashMap<>();
        responseParam.put("courseId", course.getCourseId());
        return new ServiceResponse(HttpStatus.CREATED.value(), responseParam);
    }

    public ServiceResponse listAllCourses() {
        final Iterable<Course> courses = courseDao.findAll();
        if (courses != null) {
            final List<Course> courseList = Lists.newArrayList(courses);
            return new ServiceResponse(HttpStatus.OK.value(), courseList);
        } else {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value(), "No courses");
        }
    }

    public ServiceResponse getCourseById(Long courseId) {
        LOGGER.info("Getting course by id : {}", courseId);
        Optional<Course> course = courseDao.findById(courseId);
        if (course.isPresent()) {
            LOGGER.info("Course is found : {}", course);
            return new ServiceResponse(HttpStatus.OK.value(), course);
        } else {
            LOGGER.info("Course is not found by id: {}", courseId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteCourse(Long courseId) {
        Optional<Course> course = courseDao.findById(courseId);
        if (!course.isPresent()) {
            LOGGER.info("While deleting course, it is not found by id: {}", courseId);
            return new ServiceResponse(HttpStatus.NO_CONTENT.value(), "Course is not found");
        }
        courseDao.delete(new Course(courseId));
        return new ServiceResponse(HttpStatus.OK.value(), true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse updateCourse(Course course) {
        if (null != courseDao.save(course)) {
            LOGGER.info("Course is updated with new value: {}", course);
            return new ServiceResponse(HttpStatus.OK.value(), true);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false);
        }
    }

}
