package frost.countermobile.forum.Service;

import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepo topicRepo;
    @Autowired
    CategoryService categoryService;

    public List<Topic> getTopicsByCategory(long category_id) {
        return topicRepo.findTopicByCategory_id(category_id);
    }

    public Topic createTopic(String title, String content,
                            String categorySlug, User user){
        Topic t = new Topic(title, content);
        Category c = categoryService.getCategoryBySlug(categorySlug);
        t.setCategory(c);
        t.setUser(user);
        return topicRepo.save(t);
    }

    public Topic getTopicById(long id) {
        return topicRepo.findById(id).get();
    }
}
