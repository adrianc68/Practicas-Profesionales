package org.database.dao;

import org.domain.Company;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Practicing;
import org.domain.Project;
import org.domain.Sector;
import org.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PracticingDAO implements IPracticingDAO {
    private final Database database;
    private ResultSet result;

    public PracticingDAO() {
        database = new Database();
    }

    @Override
    public void addPracticing(Practicing practicing) {
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL addPracticing(?, ?, ?, ?, ?)";
            PreparedStatement addPracticing = conn.prepareStatement(statement);
            addPracticing.setString(1, practicing.getName() );
            addPracticing.setString(2, practicing.getPhoneNumber() );
            addPracticing.setString(3, practicing.getEmail() );
            addPracticing.setInt(4, practicing.getCourse().getId() );
            addPracticing.setString(5, practicing.getEnrollment() );
            addPracticing.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PracticingDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void removePracticing(int idPracticing) {
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Practicing WHERE id_person = ?";
            PreparedStatement removePracticing = conn.prepareStatement(statement);
            removePracticing.setInt(1, idPracticing );
            removePracticing.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PracticingDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void assignProject(int idPracticing, int idProject) {
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL assignProject(?,?)";
            PreparedStatement assignProject = conn.prepareStatement(statement);
            assignProject.setInt(1, idPracticing );
            assignProject.setInt(2, idProject );
            assignProject.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PracticingDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void assignProfessor(int idPracticing, int idProfessor) {
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "UPDATE Practicing SET id_professor = ? WHERE id_person = ?";
            PreparedStatement assignProfessor = conn.prepareStatement(statement);
            assignProfessor.setInt(1, idProfessor );
            assignProfessor.setInt(2, idPracticing );
            assignProfessor.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PracticingDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void addSelectedProjectByPracticing(Practicing practicing) {

    }

    @Override
    public List<Project> getSelectedProjectsByIDPracticing(int idPracticing) {
        List<Project> projects = new ArrayList<>();
        Course course;
        Coordinator coordinator;
        Company company;
        Project project;
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT P.id_project, P.name, P.duration, P.schedule, P.general_purpose, P.general_description, P.id_company, P.charge_responsable, P.name_responsable, P.email_responsable, C.id_company, C.name, C.address, C.email, C.state, C.direct_users, C.indirect_users, C.sector, C.city, C.phoneNumber, CT.cubicle, CT.staff_number, Pe.id_person, Pe.name, Pe.phoneNumber, Pe.email, CS.id_course, CS.NRC, CS.period, CS.name FROM Project AS P INNER JOIN Practicing_Selected_Projects AS PSP ON PSP.selected_project = P.id_project AND PSP.id_person = ? INNER JOIN Company AS C ON  P.id_company = C.id_company INNER JOIN Coordinator AS CT ON C.id_coordinator = CT.id_person INNER JOIN Person AS Pe ON C.id_coordinator = Pe.id_person INNER JOIN Course AS CS ON C.id_course = CS.id_course and (SELECT max(id_course) FROM Course)";
            PreparedStatement queryProjects = conn.prepareStatement(statement);
            queryProjects.setInt(1, idPracticing);
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
            Logger.getLogger(PracticingDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return projects;
    }



}
