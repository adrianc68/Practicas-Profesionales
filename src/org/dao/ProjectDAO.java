package org.dao;

import org.domain.Project;
import org.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectDAO implements IProjectDAO {
    private final Database database;
    private ResultSet result;

    public ProjectDAO() {
        database = new Database();
    }

    @Override
    public void addProject(Project project) {
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "INSERT INTO Project (duration, schedule, general_purpose, general_description, id_company, charge_responsable, name_responsable, email_responsable) values (?,?,?,?,?,?,?,?)";
            PreparedStatement insertProject = conn.prepareStatement(statement);
            insertProject.setFloat(1,project.getDuration() );
            insertProject.setString(2, project.getSchedule() );
            insertProject.setString(3, project.getGeneralPurpose() );
            insertProject.setString(4, project.getGeneralDescription() );
            insertProject.setInt(5, project.getCompany().getId() );
            insertProject.setString(6, project.getChargeResponsable() );
            insertProject.setString(7, project.getNameResponsable() );
            insertProject.setString(8, project.getEmailResponsable() );
            insertProject.executeUpdate();
            result = insertProject.executeQuery("SELECT LAST_INSERT_ID()");
            result.next();
            int projectID = result.getInt(1);
            project.setId(projectID);
            Map<String, Iterator> multivaluedAttributes = new HashMap<>();
            multivaluedAttributes.put("INSERT INTO Project_Activities(activity, id_project) VALUES(?, ?)", project.getActivities().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Responsabilities(responsability, id_project) VALUES(?,?)", project.getResponsibilities().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Mediate_Objetives(objetive, id_project) VALUES(?,?)", project.getMediateObjectives().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Methodologies(methodology, id_project) VALUES(?,?)", project.getMethodologies().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Resources(resource, id_project) VALUES(?,?)", project.getResources().iterator() );
            for ( Map.Entry<String, Iterator> entry : multivaluedAttributes.entrySet() ) {
                insertProject = conn.prepareStatement( entry.getKey() );
                Iterator listIterator = entry.getValue();
                while( listIterator.hasNext() ) {
                    insertProject.setString(1, listIterator.next().toString() );
                    insertProject.setInt(2, projectID);
                    insertProject.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void removeProjectByID(int idProject) {
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL removeProject(?)";
            PreparedStatement removeProject = conn.prepareStatement(statement);
            removeProject.setInt(1, idProject );
            removeProject.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        return null;
    }
}
