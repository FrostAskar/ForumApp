package frost.countermobile.forum.Service;

import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Repository.ReplyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.List;

@Service
public class ReplyService {

    @Autowired
    ReplyRepo replyRepo;
    @Autowired
    PermissionService permissionService;

    public Reply createReply(String content, Topic topic, User user) {
        Reply reply = new Reply(content);
        reply.setTopic(topic);
        reply.setUser(user);
        return replyRepo.save(reply);
    }

    public List<Reply> getRepliesByTopicId(long topicId) {
        List<Reply> replies = replyRepo.findAllRepliesByTopic_id(topicId);
        //Assign _id = id
        for (Reply r: replies) {
            r.set_id(r.getId());
        }
        return replies;
    }

    public Reply getReplyById(long id) {
        return replyRepo.findById(id).get();
    }

    public void deleteReply(long id) {
        replyRepo.deleteById(id);
    }

    @Transactional
    public void modifyReply(Reply reply, String content) {
        replyRepo.updateReply(reply.getId(), content, Instant.now());
    }

}
