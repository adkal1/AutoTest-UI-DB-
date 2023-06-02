package db.models;

import java.util.Objects;

public class TestResultModel {
    private int id;
    private String name;
    private int statusId;
    private String methodName;
    private int projectId;
    private int sessionId;
    private String startTime;
    private String endTime;
    private String env;
    private String browser;
    private int authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestResultModel that)) return false;
        return id == that.id && statusId == that.statusId && projectId == that.projectId && sessionId == that.sessionId && authorId == that.authorId && Objects.equals(name, that.name) && Objects.equals(methodName, that.methodName) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(env, that.env) && Objects.equals(browser, that.browser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, statusId, methodName, projectId, sessionId, startTime, endTime, env, browser, authorId);
    }

    public TestResultModel(String name, int statusId, String methodName, int projectId, int sessionId, String startTime, String endTime, String env, String browser, int authorId) {
        this.name = name;
        this.statusId = statusId;
        this.methodName = methodName;
        this.projectId = projectId;
        this.sessionId = sessionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.env = env;
        this.browser = browser;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
