package frost.countermobile.forum.Controller;

import frost.countermobile.forum.Exception.IncorrectLoginException;
import frost.countermobile.forum.Exception.IncorrectPasswordException;
import frost.countermobile.forum.Exception.IncorrectRegisterException;
import frost.countermobile.forum.Form.LoginForm;
import frost.countermobile.forum.Form.PasswordForm;
import frost.countermobile.forum.Form.RegisterForm;
import frost.countermobile.forum.Form.UserForm;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import frost.countermobile.forum.Service.PermissionService;
import frost.countermobile.forum.Service.TokenService;
import frost.countermobile.forum.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    UserService userService;
    TokenService tokenService;
    PermissionService permissionService;
    CategoryService categoryService;

    public UserController(UserService userService,
                          TokenService tokenService,
                          PermissionService permissionService,
                          CategoryService categoryService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.permissionService = permissionService;
        this.categoryService = categoryService;
    }

    @PostMapping("/register")
    @CrossOrigin
    public Map<String, String> registerUser(@RequestBody RegisterForm registerForm,
                                            HttpServletResponse resp) {
        Map<String, String> map = new HashMap<>();
        try {
            User user = userService.createUser(registerForm.getName(),
                                               registerForm.getEmail(),
                                               registerForm.getPassword(),
                                               registerForm.getRole());
            userService.save(user);
            map.put("message", "done");
        } catch (IncorrectRegisterException e) {
            map.put("message", e.getMessage());
            resp.setStatus(400);
        }
        return map;
    }

    @PostMapping("/login")
    @CrossOrigin
    public Map<String, Object> loginUser(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        User user = null;
        Map<String, Object> map = new HashMap<>();
        try {
            user = userService.autenticateUser(loginForm.getEmail(), loginForm.getPassword());
            user = userService.generateUser(user);
            response.setStatus(200);
        } catch (IncorrectLoginException e) {
            map.put("message", e.getMessage());
            response.setStatus(401);
            return map;
        }
        String token = tokenService.createToken(user);
        map.put("user", user);
        map.put("token", token);
        return map;
    }

    @GetMapping("/getprofile")
    @CrossOrigin
    public User getProfile(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return user;
    }

    @PutMapping("/profile")
    @CrossOrigin
    public Map<String, Object> modifyUser(HttpServletRequest req,
                                          @RequestBody UserForm userForm) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) req.getAttribute("user");
        user = userService.modifyUser(userForm.getName(), userForm.getEmail(), user.getId());
        user = userService.generateUser(user);
        String token = tokenService.createToken(user);
        map.put("token", token);
        map.put("user", user);
        return map;
    }

    @PutMapping("/profile/password")
    @CrossOrigin
    public Object modifyPassword(HttpServletRequest req,
                                 HttpServletResponse resp,
                                 @RequestBody PasswordForm passwordForm) {
        User user = (User) req.getAttribute("user");
        try {
            userService.modifyPassword(passwordForm.getCurrentPassword(), passwordForm.getNewPassword(), user);
            return true;
        } catch (IncorrectPasswordException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            resp.setStatus(e.getStatusCode());
            return error;
        }
    }
}
