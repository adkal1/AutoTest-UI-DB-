package db.utilits;

import db.models.TestResultModel;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static db.DataBaseConnection.getConnection;
import static db.methods.TestResultMethods.getTestResult;
import static db.utilits.dateNumUtil.randomNum;

public class sqlUtil {
    public static int getIdStmt(PreparedStatement stmt) {
        ResultSet rs = null;
        try {
            rs = stmt.getGeneratedKeys();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }


    public static List<TestResultModel> getListRandom() {
        List<TestResultModel> randomValues = new ArrayList<>();
        String num = StringUtils.repeat(String.valueOf(randomNum(9)), 2);
        String sql = "SELECT id FROM test WHERE id LIKE  ? ? ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, "%");
            stmt.setString(2, num);
            stmt.setString(3, "%");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                randomValues.add(getTestResult(resultSet.getInt("id")));
            }
            Collections.shuffle(randomValues, new Random());
            if (randomValues.size() > 10) {
                return randomValues.subList(0, 10);
            }
            return randomValues;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
