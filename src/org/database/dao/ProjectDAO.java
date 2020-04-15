package org.database.dao;

import org.domain.Company;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Project;
import org.domain.Sector;
import org.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public void updateProjectInformation(Project project) {
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "UPDATE Project SET name = ?, duration = ?, schedule = ?, general_purpose = ?, general_description = ?, charge_responsable = ?, name_responsable = ?, email_responsable = ? WHERE id_project = ?";
            PreparedStatement updateProjectInformation = conn.prepareStatement(statement);
            updateProjectInformation.setString(1, project.getName() );
            updateProjectInformation.setFloat(2,project.getDuration() );
            updateProjectInformation.setString(3, project.getSchedule() );
            updateProjectInformation.setString(4, project.getGeneralPurpose() );
            updateProjectInformation.setString(5, project.getGeneralDescription() );
            updateProjectInformation.setString(6, project.getChargeResponsable() );
            updateProjectInformation.setString(7, project.getNameResponsable() );
            updateProjectInformation.setString(8, project.getEmailResponsable() );
            int projectID = project.getId();
            updateProjectInformation.setInt(9, projectID);
            updateProjectInformation.executeUpdate();
            String statementRemoveMultivaluedAttributes = "CALL removeMultivaluedAttributesProject(?)";
            updateProjectInformation = conn.prepareStatement(statementRemoveMultivaluedAttributes);
            updateProjectInformation.setInt(1, projectID);
            updateProjectInformation.executeUpdate();
            Map<String, Iterator> multivaluedAttributes = new HashMap<>();
            multivaluedAttributes.put("INSERT INTO Project_Activities(activity, id_project) VALUES(?, ?)", project.getActivities().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Responsabilities(responsability, id_project) VALUES(?,?)", project.getResponsibilities().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Mediate_Objetives(objetive, id_project) VALUES(?,?)", project.getMediateObjectives().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Methodologies(methodology, id_project) VALUES(?,?)", project.getMethodologies().iterator() );
            multivaluedAttributes.put("INSERT INTO Project_Resources(resource, id_project) VALUES(?,?)", project.getResources().iterator() );
            for ( Map.Entry<String, Iterator> entry : multivaluedAttributes.entrySet() ) {
                updateProjectInformation = conn.prepareStatement( entry.getKey() );
                Iterator listIterator = entry.getValue();
                while( listIterator.hasNext() ) {
                    updateProjectInformation.setString(1, listIterator.next().toString() );
                    updateProjectInformation.setInt(2, projectID );
                    updateProjectInformation.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public List<Project> getAllAvailableProjectsFromLastCourse() {
        String statement = "SELECT P.id_project, P.name, P.duration, P.schedule, P.general_purpose, P.general_description, P.id_company, P.charge_responsable, P.name_responsable, P.email_responsable, C.id_company, C.name, C.address, C.email, C.state, C.direct_users, C.indirect_users, C.sector, C.city, C.phoneNumber, CT.cubicle, CT.staff_number, Pe.id_person, Pe.name, Pe.phoneNumber, Pe.email, CS.id_course, CS.NRC, CS.period, CS.name FROM Project AS P INNER JOIN Company AS C ON  P.id_company = C.id_company INNER JOIN Coordinator AS CT ON C.id_coordinator = CT.id_person INNER JOIN Person AS Pe ON C.id_coordinator = Pe.id_person INNER JOIN Course AS CS ON C.id_course = CS.id_course and CS.id_course = (SELECT max(id_course) FROM Course) WHERE P.id_project NOT IN (SELECT id_project FROM (SELECT count(id_project) AS counter, id_project FROM Practicing GROUP BY id_project HAVING counter = 3) AS Count)";
        return getProjectsByStatement(statement);
    }

    @Override
    public List<Project> getAllProjectsFromLastCourse() {
        String statement = "SELECT P.id_project, P.name, P.duration, P.schedule, P.general_purpose, P.general_description, P.id_company, P.charge_responsable, P.name_responsable, P.email_responsable, C.id_company, C.name, C.address, C.email, C.state, C.direct_users, C.indirect_users, C.sector, C.city, C.phoneNumber, CT.cubicle, CT.staff_number,  Pe.id_person, Pe.name, Pe.phoneNumber, Pe.email,  CS.id_course, CS.NRC, CS.period, CS.name FROM Project AS P INNER JOIN Company AS C ON  P.id_company = C.id_company INNER JOIN Coordinator AS CT ON C.id_coordinator = CT.id_person INNER JOIN Person AS Pe ON C.id_coordinator = Pe.id_person INNER JOIN Course AS CS ON C.id_course = CS.id_course and CS.id_course = (SELECT max(id_course) FROM Course)";
        return getProjectsByStatement(statement);
    }

    private List<Project> getProjectsByStatement(String statement){
        List<Project> projects = new ArrayList<>();
        Course course;
        Coordinator coordinator;
        Company company;
        Project project;
        try(Connection conn = database.getConnection() ) {
            PreparedStatement queryProjects = conn.prepareStatement(statement);
            result = queryProjects.executeQuery();
            while( result.next() ) {
                course = new Course();
                course.setId(result.getInt("CS.id_course"));
                course.setNRC(result.getString("CS.NRC"));
                course.setPeriod(result.getString("CS.Period"));
                course.setName(result.getString("CS.name"));
                coordinator = new Coordinator();
                coordinator.setId(result.getInt("Pe.id_person"));
                coordinator.setName(result.getString("Pe.name"));
                coordinator.setPhoneNumber(result.getString("Pe.phoneNumber"));
                coordinator.setEmail(result.getString("Pe.email"));
                coordinator.setCourse(course);
                coordinator.setStaff_number(result.getString("CT.staff_number"));
                coordinator.setCubicle(result.getInt("CT.cubicle"));
                company = new Company();
                company.setId(result.getInt("C.id_company"));
                company.setName(result.getString("C.name"));
                company.setAddress(result.getString("C.address"));
                company.setEmail(result.getString("C.email"));
                company.setState(result.getString("C.state"));
                company.setPhoneNumber(result.getString("C.phoneNumber"));
                company.setDirectUsers(result.getInt("C.direct_users"));
                company.setIndirectUsers(result.getInt("C.indirect_users"));
                company.setSector(Sector.valueOf(result.getString("C.sector").toUpperCase()));
                company.setCity(result.getString("C.city"));
                company.setCoordinator(coordinator);
                company.setCourse(course);
                project = new Project();
                project.setId(result.getInt("P.id_project"));
                project.setName(result.getString("P.name"));
                project.setDuration(result.getFloat("P.duration"));
                project.setSchedule(result.getString("P.schedule"));
                project.setGeneralPurpose(result.getString("P.general_purpose"));
                project.setGeneralDescription(result.getString("P.general_description"));
                project.setCompany(company);
                project.setChargeResponsable(result.getString("P.charge_responsable"));
                project.setNameResponsable(result.getString("P.name_responsable"));
                project.setEmailResponsable(result.getString("P.email_responsable"));
                ArrayList<String> activities = new ArrayList<>();
                ArrayList<String> mediateObjetives = new ArrayList<>();
                ArrayList<String> methodologies = new ArrayList<>();
                ArrayList<String> resources = new ArrayList<>();
                ArrayList<String> responsabilities = new ArrayList<>();
                Map<ArrayList, String> multivaluedAttributes = new HashMap<>();
                multivaluedAttributes.put(activities,"SELECT * from project_activities WHERE id_project = ?");
                multivaluedAttributes.put(mediateObjetives, "SELECT * from project_mediate_objetives WHERE id_project = ?");
                multivaluedAttributes.put(methodologies, "SELECT * from project_methodologies WHERE id_project = ?");
                multivaluedAttributes.put(resources, "SELECT * from project_resources WHERE id_project = ?");
                multivaluedAttributes.put(responsabilities, "SELECT * from project_responsabilities WHERE id_project = ?");
                for( Map.Entry<ArrayList, String> entry : multivaluedAttributes.entrySet() ) {
                    PreparedStatement queryMultivaluedAttribute = conn.prepareStatement( entry.getValue() );
                    queryMultivaluedAttribute.setInt(1, project.getId());
                    ResultSet resultMultivaluedAttribute = queryMultivaluedAttribute.executeQuery();
                    while( resultMultivaluedAttribute.next() ) {
                        entry.getKey().add(resultMultivaluedAttribute.getString(1));
                    }
                }
                project.setActivities(activities);
                project.setMediateObjectives(mediateObjetives);
                project.setMethodologies(methodologies);
                project.setResources(resources);
                project.setResponsibilities(responsabilities);
                projects.add(project);
            }
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return projects;
    }

}