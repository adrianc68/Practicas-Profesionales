package org.database.dao;

import org.domain.Course;

public interface ICourseDAO {
    int addCourse(Course course);
    int removeCourseByID(int idCourse);

}
