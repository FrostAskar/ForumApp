package frost.countermobile.forum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @JsonIgnore
    String password;
    String email;
    String role;
    String avatarUrl;
    @Transient
    Map<String, Object> permissions;
    int __v;
    Long _id;


    public User(){}

    public User(String name, String password, String email, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.__v = 0;
        this.avatarUrl = "";
        String[] categories = new String[]{};
        Map<String, String> root = new HashMap<>();
        root.put("0", "own_topics:write");
        root.put("1", "own_topics:delete");
        root.put("2", "own_replies:write");
        root.put("3", "own_replies:delete");
        root.put("4", "own_categories:write");
        root.put("5", "own_categories:delete");
        this.permissions.put("categories", categories);
        this.permissions.put("root", root);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Map<String, Object> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Object> permissions) {
        this.permissions = permissions;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
