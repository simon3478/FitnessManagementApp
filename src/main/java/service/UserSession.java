package service;


public class UserSession {
    private Object user;
    private String role;

    public UserSession(Object user, String role) {
        this.user = user;
        this.role = role;
    }

    public Object getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }
}
