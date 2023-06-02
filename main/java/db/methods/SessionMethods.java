package db.methods;

import aquality.selenium.core.logging.Logger;
import db.models.Session;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static db.DataBaseConnection.getConnection;
import static db.utilits.dateNumUtil.dateTimeNow;
import static db.utilits.dateNumUtil.randomNum;
import static db.utilits.sqlUtil.*;

public class SessionMethods {
    public static Session insertSession() {
        String sql = "INSERT INTO session (session_key, created_time, build_number) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String sessionKey = String.valueOf(randomNum(99999));
            String createdTime = dateTimeNow();
            String buildNumber = String.valueOf(randomNum(9));
            stmt.setString(1, sessionKey);
            stmt.setString(2, createdTime);
            stmt.setString(3, buildNumber);
            stmt.executeUpdate();
            return new Session(getIdStmt(stmt), sessionKey, createdTime, buildNumber);
        } catch (SQLException e) {
            Logger.getInstance().error("Inserting of prepare statement to Project doesn't work");
            throw new RuntimeException(e);
        }
    }

}
