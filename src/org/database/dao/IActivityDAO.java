package org.database.dao;

import org.domain.Activity;
import java.util.List;

public interface IActivityDAO {
    boolean addActivity(Activity activity);
    boolean removeActivityByID(int idActivity);
    List<Activity> getAllActivitiesFromLastCourse();
    List<Activity> getAllActivitiesByProfessor();

}
