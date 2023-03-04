package frost.countermobile.forum.Controller;

import frost.countermobile.forum.Exception.UnathorizedDeleteException;
import frost.countermobile.forum.Form.ReplyForm;
import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import frost.countermobile.forum.Service.PermissionService;
import frost.countermobile.forum.Service.ReplyService;
import frost.countermobile.forum.Service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReplyController {

    TopicService topicService;
    ReplyService replyService;
    CategoryService categoryService;
    PermissionService permissionService;

    public ReplyController(TopicService topicService,
                           ReplyService replyService,
                           CategoryService categoryService,
                           PermissionService permissionService) {
        this.topicService = topicService;
        this.replyService = replyService;
        this.categoryService = categoryService;
        this.permissionService = permissionService;
    }

    @PostMapping("/topics/{id}/replies")
    @CrossOrigin
    public Reply submitReply(@PathVariable long id,
                             @RequestBody ReplyForm replyForm,
                             HttpServletRequest req) {
        Topic topic = topicService.getTopicById(id);
        User user = (User) req.getAttribute("user");
        Reply r = replyService.createReply(replyForm.getContent(), topic, user);
        r.set_id(r.getId());
        return r;
    }

    @DeleteMapping("/topics/{topicId}/replies/{replyId}")
    @CrossOrigin
    public Object deleteReply(@PathVariable long topicId,
                               @PathVariable long replyId,
                               HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        Reply reply = replyService.getReplyById(replyId);
        try {
            if (permissionService.verifyDeletePermission(user, "reply", reply)) {
                replyService.deleteReply(replyId);
                return true;
            }
        } catch (UnathorizedDeleteException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return error;
        }
        return false;
    }
}
