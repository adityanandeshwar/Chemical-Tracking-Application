public class Session {

    private Integer role;
    private String username;

    public Session(Integer role, String username) {
        this.role = role;
        this.username = username;
    }

    public Integer getRole() { return role; }
    public String getUsername() { return username; }

    public void clear() {
        this.role = null;
        this.username = null;
    }
}