package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

    public List<Topic> findTopicByCategory_id(long category_id);

}
