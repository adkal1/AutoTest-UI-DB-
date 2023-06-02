import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import db.models.Author;
import db.models.Project;
import db.models.TestResultModel;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


import static db.DataBaseConnection.disconnect;
import static db.methods.AuthorMethods.insertAuthor;
import static db.methods.ProjectMethods.insertProject;
import static db.methods.SessionMethods.insertSession;
import static db.methods.TestResultMethods.*;
import static db.utilits.dateNumUtil.dateTimeNow;
import static db.utilits.dateNumUtil.randomNum;
import static db.utilits.sqlUtil.*;


public class DBTest {
    private final ISettingsFile config = new JsonSettingsFile("settings.json");
    private final ISettingsFile db = new JsonSettingsFile("dbData.json");
    private List<TestResultModel> testList = new ArrayList<>();
    private String startTime;


    @DataProvider(name = "data")
    public Object[][] dataListId() {
        Author author = new Author(db.getValue("/nameAuthor").toString(),
                db.getValue("/login").toString(),
                db.getValue("/email").toString());
        Project project = new Project(db.getValue("/nameProject").toString());
        startTime = dateTimeNow();

        List<TestResultModel> randomList = getListRandom();
        for (TestResultModel randomTest : randomList) {
            randomTest.setAuthorId(insertAuthor(author).getId());
            randomTest.setProjectId(insertProject(project).getId());
            testList.add(insertTestResult(randomTest));
            Logger.getInstance().info("Test entry copied from id: " + randomTest.getId());
        }

        Object[][] array = new Object[randomList.size()][1];
        for (int i = 0; i < randomList.size(); ++i) {
            array[i][0] = randomList.get(i);
        }
        return array;
    }

    @Test(dataProvider = "data")
    public void simulateUpdateTestData(TestResultModel testResultModel) {
        Logger.getInstance().info("Step of id: " + testResultModel.getId());
        int statusId = randomNum(3);
        int sessionId = insertSession().getId();
        String endTime = dateTimeNow();
        String env = System.getProperty("user.name");
        String browser = config.getValue("/browserName").toString();
        testResultModel.setStatusId(statusId);
        testResultModel.setSessionId(sessionId);
        testResultModel.setEndTime(endTime);
        testResultModel.setEnv(env);
        testResultModel.setBrowser(browser);
        TestResultModel updateTest = updateTestResult(testResultModel);

        Assert.assertEquals(updateTest, getTestResult(testResultModel.getId()), "Information doesn't add to SQL");

    }

    @AfterTest
    public void delete() {
        for (TestResultModel testResult : testList) {
            int idDeleteTest = deleteTestData(testResult).getId();
            Logger.getInstance().info("Deleted entry from id: " + idDeleteTest);
            Assert.assertNull(getTestResult(idDeleteTest), "The records haven't been deleted");
            disconnect();
        }
    }
}
