package frost.countermobile.forum.Service;

import frost.countermobile.forum.DTO.Credential;
import frost.countermobile.forum.Exception.IncorrectLoginException;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Repository.UserRepo;
import frost.countermobile.forum.Util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    CategoryService categoryService;
    @Autowired
    PermissionService permissionService;

    PasswordEncoder passwordEncoder = new PasswordEncoder();

    public void save(User user) {
        userRepo.save(user);
    }

    public User createUserFromCredentials(Credential credentials) {
        String passEncoded = passwordEncoder.encodePass(credentials.getPassword());
        return new User(credentials.getName(), passEncoded, credentials.getEmail(), "admin");
    }

    public User autenticateUser(String email, String password) {
        List<User> user = userRepo.findByEmail(email);
        if (user.size() == 0){
            throw new IncorrectLoginException("User does not exist");
        }
        String tryPass = passwordEncoder.encodePass(password);
        if (!user.get(0).getPassword().equals(tryPass)) {
            throw new IncorrectLoginException("Email or password incorrect");
        }
        return user.get(0);
    }

    public List<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Map<String, Object> userToJSON(User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("AvatarUrl", user.getAvatarUrl());
        result.put("Email", user.getEmail());
        result.put("Id", user.getId());
        result.put("name", user.getName());
        result.put("permission", user.getPermissions());
        result.put("role", user.getRole());
        result.put("__v", 0);
        result.put("_id", user.getId());
        return result;
    }

    public User generateUser(User user) {
        User u = user;
        u.set_id(user.getId());
        u.setAvatarUrl("");
        Map<String, Object> permissions = new HashMap<>();
        permissions.put("categories", categoryService.getCategories());
        permissions.put("root", permissionService.setPermissions(u.getRole()));
        u.setPermissions(permissions);
        return u;
    }
}
