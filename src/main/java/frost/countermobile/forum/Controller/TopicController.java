package frost.countermobile.forum.Controller;

import frost.countermobile.forum.Form.TopicForm;
import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import frost.countermobile.forum.Service.ReplyService;
import frost.countermobile.forum.Service.TopicService;
import frost.countermobile.forum.Service.UserService;
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

    public TopicController(CategoryService categoryService,
                           TopicService topicService,
                           ReplyService replyService,
                           UserService userService) {
        this.categoryService = categoryService;
        this.topicService = topicService;
        this.replyService = replyService;
        this.userService = userService;
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
        Topic t = topicService.createTopic(topicForm.getTitle(), topicForm.getContent(),
                                 topicForm.getCategory(), user);
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
}
