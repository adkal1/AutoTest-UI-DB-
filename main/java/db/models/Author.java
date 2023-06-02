package db.models;

public class Author {
    private int id;
    private String name;
    private String login;
    private String email;

    public Author(String name, String login, String email) {
        this.name = name;
        this.login = login;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
