package frost.countermobile.forum.Service;

import frost.countermobile.forum.Model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PermissionService {

    @Autowired
    CategoryService categoryService;

    //Creates root in permissions
    public String[] setPermissions(String userRole) {
        String[] result = {};
        switch(userRole) {
            case "admin":
                result = new String[]{
                    "own_topics:write",
                    "own_topics:delete",
                    "own_replies:write",
                    "own_replies:delete",
                    "categories:write",
                    "categories:delete"
                };
                break;
        }
        return result;
    }
}
