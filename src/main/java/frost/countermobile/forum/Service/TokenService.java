package frost.countermobile.forum.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import frost.countermobile.forum.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${token.secret}")
    String tokenSecret;

    @Value("${token.expire}")
    long tokenExpire;

    @Autowired
    PermissionService permissionService;

    public String createToken(User user) {
        Map<String, Object> payload = createPayloadForToken(user);
        String t = JWT.create()
                .withPayload(payload)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpire))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return t;
    }

    private Map<String, Object> createPayloadForToken(User user) {
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> permission = new HashMap<>();
//        permission.put("categories", new String[]{""});
        Map<String, Object> root = new HashMap<>();
        root.put("root", permissionService.setPermissions(user.getRole()));
        payload.put("role", user.getRole());
        payload.put("_id", user.getId());
        payload.put("email", user.getEmail());
        payload.put("name", user.getName());
        payload.put("__v", 0);
        payload.put("avatarUrl", user.getAvatarUrl());
        payload.put("id", user.getId());
        payload.put("permission", permission);
        payload.put("iat", new Date(System.currentTimeMillis()));
        return payload;
    }

    public String getUser(String token) {
        String user= JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getClaim("email")
                .asString();
        return user;
    }
}
