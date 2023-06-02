package db.methods;

import aquality.selenium.core.logging.Logger;
import db.models.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static db.DataBaseConnection.getConnection;
import static db.utilits.sqlUtil.getIdStmt;

public class ProjectMethods {
    public static Project insertProject(Project project) {
        if (!isExistProject(project)) {
            String sql = "INSERT INTO project (name) VALUES (?)";
            try {
                PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, project.getName());
                stmt.executeUpdate();
                project.setId(getIdStmt(stmt));
                return project;
            } catch (SQLException e) {
                Logger.getInstance().error("Inserting of prepare statement to Project doesn't work");
                throw new RuntimeException(e);
            }
        }
        return project;
    }

    private static boolean isExistProject(Project project) {
        String sql = "SELECT * FROM project WHERE name = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, project.getName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                project.setId(rs.getInt("id"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            Logger.getInstance().error("Selecting of statement from Project doesn't work");
            throw new RuntimeException(e);
        }
    }
}
