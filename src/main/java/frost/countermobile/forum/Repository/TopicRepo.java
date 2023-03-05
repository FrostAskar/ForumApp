package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

    List<Topic> findTopicByCategory_id(long category_id);

    @Modifying
    @Query("update Topic set title = :title, content = :content, category = :category, updatedAt = :now where id = :topicId")
    void updateTopic(String title, String content, Category category, long topicId, Instant now);
}
