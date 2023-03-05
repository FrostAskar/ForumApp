package frost.countermobile.forum.Controller;

import frost.countermobile.forum.Exception.UnathorizedDeleteException;
import frost.countermobile.forum.Form.TopicForm;
import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {

    CategoryService categoryService;
    TopicService topicService;
    ReplyService replyService;
    UserService userService;
    PermissionService permissionService;

    public TopicController(CategoryService categoryService,
                           TopicService topicService,
                           ReplyService replyService,
                           UserService userService,
                           PermissionService permissionService) {
        this.categoryService = categoryService;
        this.topicService = topicService;
        this.replyService = replyService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @GetMapping("/categories/{slug}/topics")
    @CrossOrigin
    public List<Topic> getTopicsFromCategory(@PathVariable String slug) {
//        Map<String, Object> map = new HashMap<>();
        Category category = categoryService.getCategoryBySlug(slug);
        List<Topic> topics = topicService.getTopicsByCategory(category.getId());
        for(Topic t : topics) {
            t.set_id(t.getId());
        }
        return topics;
    }

    @PostMapping("/topics")
    @CrossOrigin
    public Topic createTopic(@RequestBody TopicForm topicForm,
                            HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        Category category = categoryService.getCategoryBySlug(topicForm.getCategory());
        Topic t = topicService.createTopic(topicForm.getTitle(), topicForm.getContent(),
                                 category, user);
        //After saving a topic, update in order to make the _id same value as id
        t.set_id(t.getId());
        return t;
    }

    @GetMapping("/topics/{id}")
    @CrossOrigin
    public Map<String, Object> getTopic(@PathVariable long id,
                                        HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        Topic t = topicService.getTopicById(id);
        t.set_id(t.getId());
        map.put("category", t.getCategory());
        map.put("content", t.getContent());
        map.put("createdAt", t.getCreatedAt());
        map.put("id", t.getId());
        map.put("numberOfReplies", 0);
        List<Reply> replies = replyService.getRepliesByTopicId(t.getId());
        map.put("replies", replies);
        map.put("title", t.getTitle());
        map.put("updateAt", t.getUpdatedAt());
        User user = userService.generateUser(t.getUser());
        map.put("user", user);
        map.put("views", 0);
        map.put("__v", 0);
        map.put("_id", t.getId());

        return map;
    }

    @DeleteMapping("/topics/{id}")
    @CrossOrigin
    public Object deleteTopic(@PathVariable long id,
                               HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        Topic topic = topicService.getTopicById(id);
        try {
            if (permissionService.verifyDeletePermission(user, "topic", topic)) {
                topicService.deleteTopic(topic.getId());
                return true;
            }
        } catch (UnathorizedDeleteException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return error;
        }
        return false;
    }

    @PutMapping("/topics/{id}")
    @CrossOrigin
    public Category modifyTopic(@RequestBody TopicForm topicForm,
                            @PathVariable long id) {
        Category category = categoryService.getCategoryBySlug(topicForm.getCategory());
        topicService.modifyTopic(topicForm.getTitle(), topicForm.getContent(), category, id);
        category = categoryService.getCategoryBySlug(topicForm.getCategory());
        return category;
    }
}
