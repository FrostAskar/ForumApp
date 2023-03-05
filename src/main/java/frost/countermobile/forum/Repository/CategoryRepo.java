package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByTitle(String title);

    List<Category> findBySlug(String slug);

    @Modifying
    @Query ("update Category set title = :title, description = :description where id = :id")
    void updateCategory(String title, String description, long id);
}
