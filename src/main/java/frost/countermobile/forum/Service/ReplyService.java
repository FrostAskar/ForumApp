package frost.countermobile.forum.Service;

import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Repository.ReplyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    ReplyRepo replyRepo;

    public Reply createReply(String content, Topic topic, User user) {
        Reply reply = new Reply(content);
        reply.setTopic(topic);
        reply.setUser(user);
        return replyRepo.save(reply);
    }

    public List<Reply> getRepliesByTopicId(long topicId) {
        return replyRepo.findAllRepliesByTopic_id(topicId);
    }

}
