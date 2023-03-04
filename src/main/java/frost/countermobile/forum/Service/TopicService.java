package frost.countermobile.forum.Service;

import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Reply;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Repository.ReplyRepo;
import frost.countermobile.forum.Repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepo topicRepo;
    @Autowired
    ReplyRepo replyRepo;
    @Autowired
    ReplyService replyService;

    public List<Topic> getTopicsByCategory(long category_id) {
        return topicRepo.findTopicByCategory_id(category_id);
    }

    public Topic createTopic(String title, String content,
                            Category category, User user){
        Topic t = new Topic(title, content);
        t.setCategory(category);
        t.setUser(user);
        return topicRepo.save(t);
    }

    public Topic getTopicById(long id) {
        return topicRepo.findById(id).get();
    }

    public void deleteTopic(long topicId) {
        List<Reply> repliesInTopic = replyRepo.findAllRepliesByTopic_id(topicId);
        if (repliesInTopic.size() > 0) {
            for (Reply r : repliesInTopic) {
                replyService.deleteReply(r.getId());
            }
        }
        topicRepo.deleteById(topicId);
    }
}
