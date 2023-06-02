package db.models;

public class Session {
    private int id;
    private String sessionKey;
    private String createdTime;
    private String buildNumber;

    public Session(int id, String sessionKey, String createdTime, String buildNumber) {
        this.id = id;
        this.sessionKey = sessionKey;
        this.createdTime = createdTime;
        this.buildNumber = buildNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }
}
