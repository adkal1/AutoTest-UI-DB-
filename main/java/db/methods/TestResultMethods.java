package db.methods;

import aquality.selenium.core.logging.Logger;
import db.models.TestResultModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static db.DataBaseConnection.getConnection;
import static db.utilits.sqlUtil.*;

public class TestResultMethods {
    public static TestResultModel insertTestResult(TestResultModel testResultModel) {
        String sql = "INSERT INTO test (name, status_id, method_name, project_id, session_id, start_time, end_time, env, browser, author_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, testResultModel.getName());
            stmt.setInt(2, testResultModel.getStatusId());
            stmt.setString(3, testResultModel.getMethodName());
            stmt.setInt(4, testResultModel.getProjectId());
            stmt.setInt(5, testResultModel.getSessionId());
            stmt.setString(6, testResultModel.getStartTime());
            stmt.setString(7, testResultModel.getEndTime());
            stmt.setString(8, testResultModel.getEnv());
            stmt.setString(9, testResultModel.getBrowser());
            stmt.setInt(10, testResultModel.getAuthorId());
            stmt.executeUpdate();
            testResultModel.setId(getIdStmt(stmt));
            return testResultModel;
        } catch (SQLException e) {
            Logger.getInstance().error("Inserting of prepare statement to TestResult doesn't work");
            throw new RuntimeException(e);
        }
    }

    public static TestResultModel updateTestResult(TestResultModel testResultModel) {
        String sql = "UPDATE test SET status_id = ?, session_id = ?, start_time = ?, end_time = ?, env = ?, browser=? WHERE id = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, testResultModel.getStatusId());
            stmt.setInt(2, testResultModel.getSessionId());
            stmt.setString(3, testResultModel.getStartTime());
            stmt.setString(4, testResultModel.getEndTime());
            stmt.setString(5, testResultModel.getEnv());
            stmt.setString(6, testResultModel.getBrowser());
            stmt.setInt(7, testResultModel.getId());
            stmt.executeUpdate();
            return testResultModel;
        } catch (SQLException e) {
            Logger.getInstance().error("Updating of prepare statement to TestResult doesn't work");
            throw new RuntimeException(e);
        }
    }
    public static TestResultModel deleteTestData(TestResultModel testResultModel) {
        String sql = "DELETE FROM test WHERE id= ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, testResultModel.getId());
            stmt.executeUpdate();
            return testResultModel;
        } catch (SQLException e) {
            Logger.getInstance().error("Deleting of statement from Test doesn't work");
            throw new RuntimeException(e);
        }
    }

    public static TestResultModel getTestResult(int id) {
        String sql = "SELECT * FROM test WHERE id = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                TestResultModel testResultModel = new TestResultModel(
                        resultSet.getString("name"),
                        resultSet.getInt("status_id"),
                        resultSet.getString("method_name"),
                        resultSet.getInt("project_id"),
                        resultSet.getInt("session_id"),
                        resultSet.getString("start_time"),
                        resultSet.getString("end_time"),
                        resultSet.getString("env"),
                        resultSet.getString("browser"),
                        resultSet.getInt("author_id"));
                testResultModel.setId(id);
                return testResultModel;
            }
        } catch (SQLException e) {
            Logger.getInstance().error("Selecting of statement from Test doesn't work");
            throw new RuntimeException(e);
        }
        return null;
    }
}
