package frost.countermobile.forum.Controller;

import frost.countermobile.forum.Form.CategoryForm;
import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {this.categoryService = categoryService;}

    @GetMapping("/categories")
    @CrossOrigin
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/categories")
    @CrossOrigin
    public void createCategory(@RequestBody CategoryForm categoryForm) {
        categoryService.createCategory(categoryForm.getTitle(), categoryForm.getDescription());
    }

    @GetMapping("/categories/{slug}")
    @CrossOrigin
    public Category getCategoryFromTopic(@PathVariable String slug) {
        return categoryService.getCategoryBySlug(slug);
    }

}
