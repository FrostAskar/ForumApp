package frost.countermobile.forum.Repository;

import frost.countermobile.forum.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByTitle(String title);

    List<Category> findBySlug(String slug);
}