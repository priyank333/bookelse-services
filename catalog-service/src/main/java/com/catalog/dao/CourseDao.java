package com.catalog.dao;

import com.catalog.model.Course;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Priyank Agrawal
 */
public interface CourseDao extends CrudRepository<Course, Long> {

}