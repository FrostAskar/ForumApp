package frost.countermobile.forum.Service;

import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.Topic;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    TopicService topicService;

    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    public Category createCategory(String title, String description) {
        Category category = new Category(title, description);
        category.setColor(colorForCategory());
        category.setSlug(slugTitle(title));
        return categoryRepo.save(category);
    }

    public Category getCategoryBySlug(String slug) {
        return categoryRepo.findBySlug(slug).get(0);
    }

    public String colorForCategory() {
        String color = "";
        int rng = new Random().nextInt(256);
        color = "hsl("+rng+", 50%, 50%)";
        return color;
    }

    public String slugTitle(String title) {
        /*First removes all special characters from the title,
        then transforms all spaces into dashes,
        then lowercases them*/
        String result = title.replaceAll("[^a-zA-Z ]", "")
                .replaceAll("[\\s+]", "-")
                .toLowerCase();
        int coincidencesInTitle = categoryRepo.findByTitle(title).size();
        //If there is more than 1 category with same title, differentiate slugs by adding the number
        if (coincidencesInTitle > 0) {
            result += "-" + coincidencesInTitle;
        }

        return result;
    }

    public void deleteCategory(long categoryId) {
        List<Topic> topicsInCategory = topicService.getTopicsByCategory(categoryId);
        if (topicsInCategory.size() > 0) {
            for(Topic t: topicsInCategory) {
                topicService.deleteTopic(t.getId());
            }
        }
        categoryRepo.deleteById(categoryId);
    }
}
