package frost.countermobile.forum.DTO;

/*
    DTO used to summarize all values used at registry and at login
 */
public class Credential {

    String email;

    String password;

    String name;

    String role;

    String moderateCategory;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getModerateCategory() {
        return moderateCategory;
    }

    public void setModerateCategory(String moderateCategory) {
        this.moderateCategory = moderateCategory;
    }
}
