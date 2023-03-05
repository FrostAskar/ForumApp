package frost.countermobile.forum.Service;

import frost.countermobile.forum.Exception.IncorrectLoginException;
import frost.countermobile.forum.Exception.IncorrectPasswordException;
import frost.countermobile.forum.Exception.IncorrectRegisterException;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Repository.UserRepo;
import frost.countermobile.forum.Util.PasswordEncoder;
import jakarta.transaction.Transactional;
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

    public User createUser(String name, String email, String pass, String role) {
        List<User> userCheck = userRepo.findByEmail(email);
        if (userCheck.size() > 0) {
            throw new IncorrectRegisterException("This user already exists");
        }
        String passEncoded = passwordEncoder.encodePass(pass);
        return new User(name, passEncoded, email, "admin");
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

    @Transactional
    public User modifyUser(String name, String email, long id) {
        userRepo.updateUser(name, email, id);
        return userRepo.findById(id).get();
    }

    @Transactional
    public void modifyPassword(String currentPassword, String newPassword, User user) {
        if (currentPassword.equals(newPassword)){
            throw new IncorrectPasswordException("Your new password cannot be the same as the old password", 400);
        } else if (!passwordEncoder.encodePass(currentPassword).equals(user.getPassword())) {
            throw new IncorrectPasswordException("Your current password is wrong!", 401);
        }
        String encodedNewPass = passwordEncoder.encodePass(newPassword);
        userRepo.updatePassword(encodedNewPass, user.getId());
    }
}
