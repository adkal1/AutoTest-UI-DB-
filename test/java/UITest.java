import db.models.Author;
import db.models.Project;
import db.models.Session;
import db.models.TestResultModel;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.GamePage;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.Assert;

import static db.DataBaseConnection.disconnect;
import static db.methods.AuthorMethods.insertAuthor;
import static db.methods.ProjectMethods.insertProject;
import static db.methods.SessionMethods.insertSession;
import static db.methods.StatusMethod.getStatus;
import static db.methods.TestResultMethods.getTestResult;
import static db.methods.TestResultMethods.insertTestResult;
import static db.utilits.dateNumUtil.dateTimeNow;
import static pages.GamePage.*;
import static utilits.FileUtil.uploadFile;


public class UITest {
    private final GamePage gamePage = new GamePage();
    private final ISettingsFile environment = new JsonSettingsFile("testData.json");
    private final ISettingsFile db = new JsonSettingsFile("dbData.json");
    private final ISettingsFile config = new JsonSettingsFile("settings.json");
    private String startTime;
    private int idSession;

    @BeforeTest
    public void getSession() {
        Session session = insertSession();
        idSession = session.getId();
    }

    @BeforeMethod()
    public void setUp() {
        startTime = dateTimeNow();
        Browser browser = AqualityServices.getBrowser();
        browser.maximize();
        browser.goTo(environment.getValue("/url").toString());
        browser.waitForPageToLoad();
    }

    @Test(description = "Verify that its possible to random valid password, email, accept the terms, choose 2 random interests, upload image, click 'Next' button")
    public void userInterface() {
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "Welcome page isn't open");
        mainPage.clickStartLink();
        Assert.assertTrue(loginPage.state().waitForDisplayed(), "Login page(the '1' card) isn't open");
        loginPage.setLoginField();
        loginPage.setPasswordField();
        loginPage.setDomainField();
        loginPage.clickComboBox();
        loginPage.selectAcceptCheckBox();
        gamePage.clickNextButton();
        Assert.assertTrue(avatarInterestsPage.state().waitForDisplayed(), "Avatar&Interests page (the '2' card) isn't open");
        avatarInterestsPage.uncheckAllCheckBoxes();
        avatarInterestsPage.checkRandomCheckBoxes(environment.getValue("/numberOfInterests").toString());
        avatarInterestsPage.clickDownloadImage();
        avatarInterestsPage.clickUploadImage();
        uploadFile();
        avatarInterestsPage.waitUploadImage();
        gamePage.clickNextButton();
        Assert.assertTrue(personalDetailsPage.state().waitForDisplayed(), "Personal Details page (the '3' card) is open");
    }

    @Test(description = "Verify that it's possible to help form content is hidden")
    public void helpForm() {
        Assert.assertTrue(mainPage.state().isDisplayed(), "Welcome page isn't open");
        mainPage.clickStartLink();
        helpForm.clickSendButton();
        Assert.assertFalse(helpForm.state().isDisplayed(), "Help form content isn't hidden");
    }

    @Test(description = "Verify that it's possible to cookies from is closed")
    public void cookies() {
        Assert.assertTrue(mainPage.state().isDisplayed(), "Welcome page isn't open");
        mainPage.clickStartLink();
        cookiesForm.clickAcceptCookiesButton();
        Assert.assertFalse(cookiesForm.state().isDisplayed(), "Timer doesn't start from'00:00'");
    }

    @Test(description = "Verify that it's possible to validate that timer starts from '00:00'")
    public void timer() {
        Assert.assertTrue(mainPage.state().isDisplayed(), "Welcome page isn't open");
        mainPage.clickStartLink();
        Assert.assertEquals(gamePage.getTextFromTimerLabel(), environment.getValue("/timerStart").toString(), "Timer doesn't start from '00:00'");
    }

    @AfterMethod()
    public void addTestResult(ITestResult result) {
        Author author = new Author(db.getValue("/nameAuthor").toString(),
                db.getValue("/login").toString(),
                db.getValue("/email").toString());
        int idAuthor = insertAuthor(author).getId();
        Project project = new Project(db.getValue("/nameProject").toString());
        int idProject = insertProject(project).getId();
        int idStatus = getStatus(result).getId();
        String endTime = dateTimeNow();
        String methodName = result.getName();
        String name = result.getMethod().getDescription();
        String env = System.getProperty("user.name");
        String browser = config.getValue("/browserName").toString();

        TestResultModel testResultModel = new TestResultModel(name, idStatus, methodName, idProject, idSession, startTime, endTime, env, browser, idAuthor);
        int idTestResult = insertTestResult(testResultModel).getId();

        Assert.assertEquals(testResultModel, getTestResult(idTestResult), "Information doesn't add to SQL");
        disconnect();

    }

    @AfterMethod
    public void quit() {
        AqualityServices.getBrowser().quit();
        AqualityServices.setDefaultBrowserFactory();
    }


}
