package db;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseConnection {
    private static Connection connection;
    private static final ISettingsFile environment = new JsonSettingsFile("testData.json");

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        environment.getValue("/urlMySql").toString(),
                        environment.getValue("/username").toString(),
                        environment.getValue("/password").toString());
            } catch (SQLException e) {
                Logger.getInstance().error("Data base doesn't connect");
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void disconnect() {
        if (connection == null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Logger.getInstance().error("Data base doesn't disconnect");
                throw new RuntimeException(e);
            }
        }
    }
}
