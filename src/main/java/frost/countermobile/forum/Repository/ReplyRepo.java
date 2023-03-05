package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ReplyRepo extends JpaRepository<Reply, Long> {

    List<Reply> findAllRepliesByTopic_id(long topicId);

    @Modifying
    @Query("update Reply set content = :content where id = :replyId")
    void updateReply(long replyId, String content);

}
