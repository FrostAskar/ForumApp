package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

    @Modifying
    @Query("update User set name = :name, email = :email where id = :id")
    void updateUser(String name, String email, long id);

    @Modifying
    @Query("update User set password = :newPass where id = :id")
    void updatePassword(String newPass, long id);
}
