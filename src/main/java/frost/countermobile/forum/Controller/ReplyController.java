package frost.countermobile.forum.Controller;

import frost.countermobile.forum.Form.ReplyForm;
import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import frost.countermobile.forum.Service.ReplyService;
import frost.countermobile.forum.Service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReplyController {

    TopicService topicService;
    ReplyService replyService;
    CategoryService categoryService;

    public ReplyController(TopicService topicService,
                           ReplyService replyService,
                           CategoryService categoryService) {
        this.topicService = topicService;
        this.replyService = replyService;
        this.categoryService = categoryService;
    }

    @PostMapping("/topics/{id}/replies")
    @CrossOrigin
    public Reply submitReply(@PathVariable long id,
                             @RequestBody ReplyForm replyForm,
                             HttpServletRequest req) {
        Topic topic = topicService.getTopicById(id);
        User user = (User) req.getAttribute("user");
        return replyService.createReply(replyForm.getContent(), topic, user);
    }
}
