package frost.countermobile.forum.Controller;

import frost.countermobile.forum.DTO.Credential;
import frost.countermobile.forum.Exception.IncorrectLoginException;
import frost.countermobile.forum.Form.LoginForm;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import frost.countermobile.forum.Service.PermissionService;
import frost.countermobile.forum.Service.TokenService;
import frost.countermobile.forum.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> registerUser(@RequestBody Credential credentials) {
        User user = userService.createUserFromCredentials(credentials);
        userService.save(user);
        return ResponseEntity.ok().build();
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
}
