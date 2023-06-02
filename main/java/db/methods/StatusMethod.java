package db.methods;

import aquality.selenium.core.logging.Logger;
import db.models.Status;
import org.testng.ITestResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static db.DataBaseConnection.getConnection;


public class StatusMethod {
    public static Status getStatus(ITestResult result) {
        String sql = "SELECT * FROM status WHERE name = ?";
        String name = null;
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            switch (result.getStatus()) {
                case ITestResult.SUCCESS -> name = "PASSED";
                case ITestResult.FAILURE -> name ="FAILED";
                case ITestResult.SKIP -> name = "SKIPPED";
            }
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt("id");
            }
            return new Status(id, name);
        } catch (SQLException e) {
            Logger.getInstance().error("Inserting of prepare statement to Status doesn't work");
            throw new RuntimeException(e);
        }
    }
}
