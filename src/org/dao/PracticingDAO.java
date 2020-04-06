package org.dao;

import org.domain.Practicing;
import org.domain.Project;
import org.util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PracticingDAO implements IPracticingDAO {
    private final Database database;
    private ResultSet result;

    public PracticingDAO() {
        database = new Database();
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
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void addSelectedProject(Practicing practicing) {

    }

    @Override
    public List<Project> getSelectedProjects(Practicing practicing) {
        return null;
    }
}
