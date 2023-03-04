package frost.countermobile.forum.Interceptor;


import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.google.gson.Gson;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import frost.countermobile.forum.Service.PermissionService;
import frost.countermobile.forum.Service.TokenService;
import frost.countermobile.forum.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;
    Gson gson = new Gson();
    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    CategoryService categoryService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        String authHeader = req.getHeader("Authorization");
        if(authHeader == null) {
            return true;
        } else {
            try {
                String token = authHeader.replace("Bearer ", "");
                String user = tokenService.getUser(token);
                User u = userService.generateUser(userService.findUserByEmail((user)).get(0));
                req.setAttribute("user", u);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return true;
            }
        }

    }
}
