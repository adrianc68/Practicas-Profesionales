package org.database.dao;

import org.domain.Course;
import java.util.List;

public interface ICourseDAO {
    boolean addCourse(Course course);
    boolean removeCourseByID(int idCourse);
    Course getLastCourse();
    List<Course> getAllCourses();

}
