package org.database.dao;

import org.domain.Course;
import java.util.List;

public class CourseDAO implements ICourseDAO {
    @Override
    public boolean addCourse(Course course) {
        return true;
    }

    @Override
    public boolean removeCourseByID(int idCourse) {
        return true;
    }

    @Override
    public List<Course> getAllCourses() {
        return null;
    }
}
