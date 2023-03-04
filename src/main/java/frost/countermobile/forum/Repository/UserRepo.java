package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
}
