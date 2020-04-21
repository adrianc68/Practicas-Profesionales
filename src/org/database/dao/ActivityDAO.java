package org.database.dao;

import org.domain.Activity;
import java.util.List;

public class ActivityDAO implements IActivityDAO {
    @Override
    public boolean addActivity(Activity activity) {
        return true;
    }

    @Override
    public boolean removeActivityByID(int idActivity) {
        return true;
    }

    @Override
    public List<Activity> getAllActivitiesFromLastCourse() {
        return null;
    }

    @Override
    public List<Activity> getAllActivitiesByProfessor() {
        return null;
    }

}
