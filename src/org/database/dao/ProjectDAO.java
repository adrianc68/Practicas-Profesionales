package org.database.dao;

import org.domain.Company;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Project;
import org.domain.Sector;
import org.database.Database;

import java.sql.*;
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

    /***
     * Add a project to database.
     * <p>
     * This method is used by coordinator when he needs to add a project
     * </p>
     * @param project the project to be added to database
     * @return int representing the project's ID
     */
    @Override
    public int addProject(Project project) {
        int projectID = 0;
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
            insertProject.executeUpdate();
            result = insertProject.executeQuery("SELECT LAST_INSERT_ID()");
            result.next();
            projectID = result.getInt(1);
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
                    insertProject.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return projectID;
    }

    /***
     * Remove a project from database.
     * <p>
     * It's used by Coordinator when he needs to remove a project for any reason.
     * </p>
     * @param idProject the project's id to be removed from database.
     * @return boolean true if project was removed.
     */
    @Override
    public boolean removeProjectByID(int idProject) {
        int idRemove = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL removeProject(?, ?)";
            CallableStatement removeProject = conn.prepareCall(statement);
            removeProject.setInt(1, idProject );
            removeProject.registerOutParameter(2, Types.INTEGER);
            removeProject.execute();
            idRemove = removeProject.getInt("idRemove");
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return idRemove != 0;
    }

    /***
     * Update the project information from a existing project in database.
     * <p>
     * This method receive a project and replaces the information in database
     * </p>
     * @param project the project with updated information.
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean updateProjectInformation(Project project) {
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
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected > 0;
    }

    /***
     * Add in the datase a project selected by a Practitioner
     * <p>
     * This method is used by the practitioner to add a project selected by himself.
     * </p>
     * @param idPractitioner practioner's id to set the selected project
     * @param idProject project's id to set.
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean addSelectedProjectByPractitionerID(int idPractitioner, int idProject) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL selectProject(?, ?)";
            PreparedStatement selectProject = conn.prepareStatement(statement);
            selectProject.setInt(1, idPractitioner);
            selectProject.setInt(2, idProject);
            rowsAffected = selectProject.executeUpdate();
            conn.commit();
        }catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected > 0;
    }

    /***
     * Get the projects selected by a practitioner.
     * <p>
     * It's used by the coordinator as a support to assign a project to a Practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an List which contain the projects selected by a practitioner.
     */
    @Override
    public List<Project> getSelectedProjectsByPractitionerID(int idPractitioner) {
        String statement = "SELECT PSP.id_person, PROJ.id_project, PROJ.name, PROJ.duration, PROJ.schedule, PROJ.general_purpose, PROJ.general_description, PROJ.id_company, PROJ.charge_responsable, PROJ.name_responsable, PROJ.email_responsable, COMP.id_company, COMP.name, COMP.address, COMP.email, COMP.state, COMP.direct_users, COMP.indirect_users, COMP.sector, COMP.city, COMP.phoneNumber, CORD.cubicle, CORD.staff_number, PERSCORD.id_person, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM (SELECT selected_project, id_person FROM Practitioner_Selected_Projects WHERE id_person = ?) AS PSP INNER JOIN Project AS PROJ ON PROJ.id_project = PROJ.id_project AND PSP.selected_project = PROJ.id_project INNER JOIN Company AS COMP ON  PROJ.id_company = COMP.id_company INNER JOIN Coordinator AS CORD ON COMP.id_coordinator = CORD.id_person INNER JOIN Person AS PERSCORD ON COMP.id_coordinator = PERSCORD.id_person INNER JOIN Course AS COUR ON COMP.id_course = COUR.id_course";
        return getProjectsByStatementAndIDPractitioner(statement, idPractitioner);
    }

    /***
     * Get the assigned project of a practitioner.
     * <p>
     * It's used by the coordinator to view which project is assigned to a practitioner
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return a Project of a Practitioner.
     */
    @Override
    public Project getAssignedProjectByPractitionerID(int idPractitioner) {
        String statement = "SELECT PROJ.id_project, PROJ.name, PROJ.duration, PROJ.schedule, PROJ.general_purpose, PROJ.general_description, PROJ.id_company, PROJ.charge_responsable, PROJ.name_responsable, PROJ.email_responsable, COMP.id_company, COMP.name, COMP.address, COMP.email, COMP.state, COMP.direct_users, COMP.indirect_users, COMP.sector, COMP.city, COMP.phoneNumber, CORD.cubicle, CORD.staff_number, PERSCORD.id_person, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM (SELECT id_person, id_project FROM Practitioner WHERE id_person = ?) AS PRAC INNER JOIN Project AS PROJ ON PRAC.id_project = PROJ.id_project INNER JOIN Company AS COMP ON  PROJ.id_company = COMP.id_company INNER JOIN Coordinator AS CORD ON COMP.id_coordinator = CORD.id_person INNER JOIN Person AS PERSCORD ON COMP.id_coordinator = PERSCORD.id_person INNER JOIN Course AS COUR ON COMP.id_course = COUR.id_course";
        List<Project> projects = getProjectsByStatementAndIDPractitioner(statement, idPractitioner);
        Project project = ( projects.isEmpty() ) ? null : projects.get(0);
        return project;
    }

    /***
     * Get all unassigned projects or available of the actual/last course from the database.
     * <p>
     * This method is intended for the coordinator and practitioners.
     * </p>
     * @return List<Project> a list containing the available projects
     */
    @Override
    public List<Project> getAllAvailableProjectsFromLastCourse() {
        String statement = "SELECT PROJ.id_project, PROJ.name, PROJ.duration, PROJ.schedule, PROJ.general_purpose, PROJ.general_description, PROJ.id_company, PROJ.charge_responsable, PROJ.name_responsable, PROJ.email_responsable, COMP.id_company, COMP.name, COMP.address, COMP.email, COMP.state, COMP.direct_users, COMP.indirect_users, COMP.sector, COMP.city, COMP.phoneNumber, CORD.cubicle, CORD.staff_number, PERSCORD.id_person, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Project AS PROJ INNER JOIN Company AS COMP ON  PROJ.id_company = COMP.id_company INNER JOIN Coordinator AS CORD ON COMP.id_coordinator = CORD.id_person INNER JOIN Person AS PERSCORD ON COMP.id_coordinator = PERSCORD.id_person INNER JOIN Course AS COUR ON COMP.id_course = COUR.id_course and COUR.id_course = (SELECT max(id_course) FROM Course) WHERE PROJ.id_project NOT IN (SELECT id_project FROM (SELECT count(id_project) AS counter, id_project FROM Practitioner GROUP BY id_project HAVING counter = 3) AS Count)";
        return getProjectsByStatement(statement);
    }

    /***
     * Get all projects of the actual/last course from the database.
     * <p>.
     * This method is used by the coordinator to verify the projects added.
     * </p>
     * @return List<Project> a list containing all projects of the actual/last course
     */
    @Override
    public List<Project> getAllProjectsFromLastCourse() {
        String statement = "SELECT PROJ.id_project, PROJ.name, PROJ.duration, PROJ.schedule, PROJ.general_purpose, PROJ.general_description, PROJ.id_company, PROJ.charge_responsable, PROJ.name_responsable, PROJ.email_responsable, COMP.id_company, COMP.name, COMP.address, COMP.email, COMP.state, COMP.direct_users, COMP.indirect_users, COMP.sector, COMP.city, COMP.phoneNumber, CORD.cubicle, CORD.staff_number, PERSCORD.id_person, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Project AS PROJ INNER JOIN Company AS COMP ON  PROJ.id_company = COMP.id_company INNER JOIN Coordinator AS CORD ON COMP.id_coordinator = CORD.id_person INNER JOIN Person AS PERSCORD ON CORD.id_person = PERSCORD.id_person INNER JOIN Course AS COUR ON COMP.id_course = COUR.id_course and COUR.id_course = (SELECT max(id_course) FROM Course)";
        return getProjectsByStatement(statement);
    }

    private List<Project> getProjectsByStatementAndIDPractitioner(String statement, int idPractitioner){
        List<Project> projects = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            PreparedStatement queryProjects = conn.prepareStatement(statement);
            queryProjects.setInt(1, idPractitioner);
            result = queryProjects.executeQuery();
            while( result.next() ) {
                Course course = new Course();
                course.setId(result.getInt("COUR.id_course"));
                course.setNRC(result.getString("COUR.NRC"));
                course.setPeriod(result.getString("COUR.Period"));
                course.setName(result.getString("COUR.name"));
                Coordinator coordinator = new Coordinator();
                coordinator.setId(result.getInt("PERSCORD.id_person"));
                coordinator.setName(result.getString("PERSCORD.name"));
                coordinator.setPhoneNumber(result.getString("PERSCORD.phoneNumber"));
                coordinator.setEmail(result.getString("PERSCORD.email"));
                coordinator.setCourse(course);
                coordinator.setStaffNumber(result.getString("CORD.staff_number"));
                coordinator.setCubicle(result.getInt("CORD.cubicle"));
                Company company = new Company();
                company.setId(result.getInt("COMP.id_company"));
                company.setName(result.getString("COMP.name"));
                company.setAddress(result.getString("COMP.address"));
                company.setEmail(result.getString("COMP.email"));
                company.setState(result.getString("COMP.state"));
                company.setPhoneNumber(result.getString("COMP.phoneNumber"));
                company.setDirectUsers(result.getInt("COMP.direct_users"));
                company.setIndirectUsers(result.getInt("COMP.indirect_users"));
                company.setSector(Sector.valueOf(result.getString("COMP.sector").toUpperCase()));
                company.setCity(result.getString("COMP.city"));
                company.setCoordinator(coordinator);
                company.setCourse(course);
                Project project = new Project();
                project.setId(result.getInt("PROJ.id_project"));
                project.setName(result.getString("PROJ.name"));
                project.setDuration(result.getFloat("PROJ.duration"));
                project.setSchedule(result.getString("PROJ.schedule"));
                project.setGeneralPurpose(result.getString("PROJ.general_purpose"));
                project.setGeneralDescription(result.getString("PROJ.general_description"));
                project.setCompany(company);
                project.setChargeResponsable(result.getString("PROJ.charge_responsable"));
                project.setNameResponsable(result.getString("PROJ.name_responsable"));
                project.setEmailResponsable(result.getString("PROJ.email_responsable"));
                List<String> activities = new ArrayList<>();
                List<String> mediateObjetives = new ArrayList<>();
                List<String> methodologies = new ArrayList<>();
                List<String> resources = new ArrayList<>();
                List<String> responsabilities = new ArrayList<>();
                List<String> immediateObjetives = new ArrayList<>();
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
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return projects;
    }

    private List<Project> getProjectsByStatement(String statement){
        List<Project> projects = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            PreparedStatement queryProjects = conn.prepareStatement(statement);
            result = queryProjects.executeQuery();
            while( result.next() ) {
                Course course = new Course();
                course.setId(result.getInt( "COUR.id_course") );
                course.setNRC(result.getString( "COUR.NRC") );
                course.setPeriod(result.getString( "COUR.Period") );
                course.setName(result.getString( "COUR.name") );
                Coordinator coordinator = new Coordinator();
                coordinator.setId(result.getInt( "PERSCORD.id_person") );
                coordinator.setName(result.getString( "PERSCORD.name") );
                coordinator.setPhoneNumber(result.getString( "PERSCORD.phoneNumber") );
                coordinator.setEmail(result.getString( "PERSCORD.email") );
                coordinator.setCourse(course);
                coordinator.setStaffNumber(result.getString( "CORD.staff_number") );
                coordinator.setCubicle(result.getInt( "CORD.cubicle"));
                Company company = new Company();
                company.setId(result.getInt( "COMP.id_company"));
                company.setName(result.getString( "COMP.name"));
                company.setAddress(result.getString( "COMP.address"));
                company.setEmail(result.getString( "COMP.email"));
                company.setState(result.getString( "COMP.state"));
                company.setPhoneNumber(result.getString( "COMP.phoneNumber"));
                company.setDirectUsers(result.getInt( "COMP.direct_users"));
                company.setIndirectUsers(result.getInt( "COMP.indirect_users"));
                company.setSector( Sector.valueOf(result.getString( "COMP.sector").toUpperCase()));
                company.setCity(result.getString( "COMP.city"));
                company.setCoordinator(coordinator);
                company.setCourse(course);
                Project project = new Project();
                project.setId(result.getInt( "PROJ.id_project"));
                project.setName(result.getString( "PROJ.name"));
                project.setDuration(result.getFloat( "PROJ.duration"));
                project.setSchedule(result.getString( "PROJ.schedule"));
                project.setGeneralPurpose(result.getString( "PROJ.general_purpose"));
                project.setGeneralDescription(result.getString( "PROJ.general_description"));
                project.setCompany(company);
                project.setChargeResponsable(result.getString( "PROJ.charge_responsable"));
                project.setNameResponsable(result.getString( "PROJ.name_responsable"));
                project.setEmailResponsable(result.getString( "PROJ.email_responsable"));
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
                    queryMultivaluedAttribute.setInt(1, project.getId() );
                    ResultSet resultMultivaluedAttribute = queryMultivaluedAttribute.executeQuery();
                    while( resultMultivaluedAttribute.next() ) {
                        entry.getValue().add( resultMultivaluedAttribute.getString(1) );
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
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return projects;
    }

}
