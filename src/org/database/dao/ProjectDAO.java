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
    /***
     * Constant for the connection to the database
     */
    private final Database database;
    /***
     * Query results
     */
    private ResultSet result;
    /***
     * ProjectDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public ProjectDAO() {
        database = new Database();
    }

    /***
     * Add a project to database.
     * <p>
     * This method receive a instance of project and then add it to database.
     * This method is used by coordinator when he needs to add a project
     * </p>
     * @param project the project to be added to database
     * @return the row number affected by this method
     */
    @Override
    public int addProject(Project project) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "INSERT INTO Project (name, duration, schedule, general_purpose, general_description, id_company, charge_responsable, name_responsable, email_responsable) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement insertProject = conn.prepareStatement(statement);
            insertProject.setString(1, project.getName());
            insertProject.setFloat(2,project.getDuration() );
            insertProject.setString(3, project.getSchedule() );
            insertProject.setString(4, project.getGeneralPurpose() );
            insertProject.setString(5, project.getGeneralDescription() );
            insertProject.setInt(6, project.getCompany().getId() );
            insertProject.setString(7, project.getChargeResponsable() );
            insertProject.setString(8, project.getNameResponsable() );
            insertProject.setString(9, project.getEmailResponsable() );
            rowsAffected += insertProject.executeUpdate();
            result = insertProject.executeQuery("SELECT LAST_INSERT_ID()");
            result.next();
            int projectID = result.getInt(1);
            project.setId(projectID);
            Map<String, Iterator> multivaluedAttributesStatementsMap = new HashMap<>();
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Activities(activity, id_project) VALUES(?, ?)", project.getActivities().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Responsabilities(responsability, id_project) VALUES(?,?)", project.getResponsibilities().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Mediate_Objetives(objetive, id_project) VALUES(?,?)", project.getMediateObjectives().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Methodologies(methodology, id_project) VALUES(?,?)", project.getMethodologies().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Resources(resource, id_project) VALUES(?,?)", project.getResources().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Immediate_Objetives(objetive, id_project) VALUES(?,?)", project.getImmediateObjetives().iterator() );
            for ( Map.Entry<String, Iterator> entry : multivaluedAttributesStatementsMap.entrySet() ) {
                insertProject = conn.prepareStatement( entry.getKey() );
                Iterator listIterator = entry.getValue();
                while( listIterator.hasNext() ) {
                    insertProject.setString(1, listIterator.next().toString() );
                    insertProject.setInt(2, projectID);
                    rowsAffected += insertProject.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Remove a project from database.
     * <p>
     * This method will remove a project from database. It's used by Coordinator when he needs
     * to remove a project for any reason.
     * </p>
     * @param idProject the project's id to be removed from database.
     * @return return the rows number affected by this method.
     */
    @Override
    public int removeProjectByID(int idProject) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL removeProject(?)";
            PreparedStatement removeProject = conn.prepareStatement(statement);
            removeProject.setInt(1, idProject );
            rowsAffected = removeProject.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Update the project information from a existing project in database.
     * <p>
     * This method receive a project and replaces the information in database
     * </p>
     * @param project the project with updated information.
     * @return the rows number affected by this method
     */
    @Override
    public int updateProjectInformation(Project project) {
        int rowsAffected = 0;
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
            rowsAffected += updateProjectInformation.executeUpdate();
            String statementRemoveMultivaluedAttributes = "CALL removeMultivaluedAttributesProject(?)";
            updateProjectInformation = conn.prepareStatement(statementRemoveMultivaluedAttributes);
            updateProjectInformation.setInt(1, projectID);
            rowsAffected += updateProjectInformation.executeUpdate();
            Map<String, Iterator> multivaluedAttributesStatementsMap = new HashMap<>();
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Activities(activity, id_project) VALUES(?, ?)", project.getActivities().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Responsabilities(responsability, id_project) VALUES(?,?)", project.getResponsibilities().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Mediate_Objetives(objetive, id_project) VALUES(?,?)", project.getMediateObjectives().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Methodologies(methodology, id_project) VALUES(?,?)", project.getMethodologies().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Resources(resource, id_project) VALUES(?,?)", project.getResources().iterator() );
            multivaluedAttributesStatementsMap.put("INSERT INTO Project_Immediate_Objetives(objetive, id_project) VALUES(?,?)", project.getImmediateObjetives().iterator());
            for ( Map.Entry<String, Iterator> entry : multivaluedAttributesStatementsMap.entrySet() ) {
                updateProjectInformation = conn.prepareStatement( entry.getKey() );
                Iterator listIterator = entry.getValue();
                while( listIterator.hasNext() ) {
                    updateProjectInformation.setString(1, listIterator.next().toString() );
                    updateProjectInformation.setInt(2, projectID );
                    rowsAffected += updateProjectInformation.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Get all unassigned projects or available of the actual/last course from the database.
     * <p>
     * This method get all available projects of the last course. This method is intended
     * for the coordinator and practitioners.
     * </p>
     * @return List<Project> a list containing the available projects
     */
    @Override
    public List<Project> getAllAvailableProjectsFromLastCourse() {
        String statement = "SELECT P.id_project, P.name, P.duration, P.schedule, P.general_purpose, P.general_description, P.id_company, P.charge_responsable, P.name_responsable, P.email_responsable, C.id_company, C.name, C.address, C.email, C.state, C.direct_users, C.indirect_users, C.sector, C.city, C.phoneNumber, CT.cubicle, CT.staff_number, Pe.id_person, Pe.name, Pe.phoneNumber, Pe.email, CS.id_course, CS.NRC, CS.period, CS.name FROM Project AS P INNER JOIN Company AS C ON  P.id_company = C.id_company INNER JOIN Coordinator AS CT ON C.id_coordinator = CT.id_person INNER JOIN Person AS Pe ON C.id_coordinator = Pe.id_person INNER JOIN Course AS CS ON C.id_course = CS.id_course and CS.id_course = (SELECT max(id_course) FROM Course) WHERE P.id_project NOT IN (SELECT id_project FROM (SELECT count(id_project) AS counter, id_project FROM Practitioner GROUP BY id_project HAVING counter = 3) AS Count)";
        return getProjectsByStatement(statement);
    }

    /***
     * Get all projects of the actual/last course from the database.
     * <p>
     * This method get all projects of the last course from the database.
     * This method is used by the coordinator.
     * </p>
     * @return List<Project> a list containing all projects of the last course
     */
    @Override
    public List<Project> getAllProjectsFromLastCourse() {
        String statement = "SELECT P.id_project, P.name, P.duration, P.schedule, P.general_purpose, P.general_description, P.id_company, P.charge_responsable, P.name_responsable, P.email_responsable, C.id_company, C.name, C.address, C.email, C.state, C.direct_users, C.indirect_users, C.sector, C.city, C.phoneNumber, CT.cubicle, CT.staff_number,  Pe.id_person, Pe.name, Pe.phoneNumber, Pe.email,  CS.id_course, CS.NRC, CS.period, CS.name FROM Project AS P INNER JOIN Company AS C ON  P.id_company = C.id_company INNER JOIN Coordinator AS CT ON C.id_coordinator = CT.id_person INNER JOIN Person AS Pe ON C.id_coordinator = Pe.id_person INNER JOIN Course AS CS ON C.id_course = CS.id_course and CS.id_course = (SELECT max(id_course) FROM Course)";
        return getProjectsByStatement(statement);
    }

    /***
     * Get the project list according to the condition received as parameter
     * <p>
     * This method get the project list according to the parameter.
     * The goal is re-use code.
     * </p>
     * @param statement
     * @return
     */
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
                ArrayList<String> immediateObjetives = new ArrayList<>();
                ArrayList<String> methodologies = new ArrayList<>();
                ArrayList<String> resources = new ArrayList<>();
                ArrayList<String> responsabilities = new ArrayList<>();
                Map<String, List<String>> multivaluedAttributesStatementsMap = new HashMap<>();
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_activities WHERE id_project = ?", activities);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_mediate_objetives WHERE id_project = ?", mediateObjetives);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_methodologies WHERE id_project = ?", methodologies);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_resources WHERE id_project = ?", resources);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_responsabilities WHERE id_project = ?", responsabilities);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_immediate_objetives WHERE id_project = ?", immediateObjetives);
                PreparedStatement queryMultivaluedAttribute = null;
                for( Map.Entry<String, List<String>> entry : multivaluedAttributesStatementsMap.entrySet() ) {
                    queryMultivaluedAttribute = conn.prepareStatement( entry.getKey() );
                    queryMultivaluedAttribute.setInt(1, project.getId());
                    ResultSet resultMultivaluedAttribute = queryMultivaluedAttribute.executeQuery();
                    while( resultMultivaluedAttribute.next() ) {
                        entry.getValue().add(resultMultivaluedAttribute.getString(1));
                    }
                }
                project.setActivities(activities);
                project.setMediateObjectives(mediateObjetives);
                project.setMethodologies(methodologies);
                project.setResources(resources);
                project.setResponsibilities(responsabilities);
                project.setImmediateObjetives(immediateObjetives);
                projects.add(project);
            }
        } catch (SQLException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return projects;
    }

}
