package db.methods;

import aquality.selenium.core.logging.Logger;
import db.models.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static db.DataBaseConnection.getConnection;
import static db.utilits.sqlUtil.getIdStmt;

public class AuthorMethods {

    public static Author insertAuthor(Author author) {
        if (!isExistAuthor(author)) {
            String sql = "INSERT INTO author (name, login, email) VALUES (?, ?, ?)";
            try {
                PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, author.getName());
                stmt.setString(2, author.getLogin());
                stmt.setString(3, author.getEmail());
                stmt.executeUpdate();
                author.setId(getIdStmt(stmt));
                return author;
            } catch (SQLException e) {
                Logger.getInstance().error("Inserting of prepare statement to Author doesn't work");
                throw new RuntimeException(e);
            }
        }
        return author;
    }

    private static boolean isExistAuthor(Author author) {
        String sql = "SELECT * FROM author WHERE name = ? AND login = ? AND email = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getLogin());
            stmt.setString(3, author.getEmail());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                author.setId(rs.getInt("id"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            Logger.getInstance().error("Selecting of statement from Author doesn't work");
            throw new RuntimeException(e);
        }
    }
}
