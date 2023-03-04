package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepo extends JpaRepository<Reply, Long> {

    List<Reply> findAllRepliesByTopic_id(long topicId);;

}
