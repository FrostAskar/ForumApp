package frost.countermobile.forum.Service;

import frost.countermobile.forum.Exception.UnathorizedDeleteException;
import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

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
            case "moderator", "user":
                result = new String[]{
                    "own_topics:write",
                    "own_topics:delete",
                    "own_replies:write",
                    "own_replies:delete"
                };
                break;
        }
        return result;
    }

    public Boolean verifyDeletePermission(User user, String option, Object target) {
        switch(option) {
            case "reply":
                Reply r = (Reply) target;
                if(user.getId() != r.getUser().getId()) {
                    throw new UnathorizedDeleteException("Only can delete own replies");
                }
                break;
            case "topic":
                Topic t = (Topic) target;
                if(user.getId() != t.getUser().getId()) {
                    throw new UnathorizedDeleteException("Only can delete own topics");
                }
                break;
            case "category":
                String[] root = (String[]) user.getPermissions().get("root");
                if(root.length == 6 &&
                    root[5].equals("categories:delete")){
                    break;
                } else {
                    throw new UnathorizedDeleteException("Only admin can delete categories");
                }
        }
        return true;
    }
}
