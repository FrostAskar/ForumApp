package frost.countermobile.forum.Controller;

import frost.countermobile.forum.Exception.UnathorizedDeleteException;
import frost.countermobile.forum.Form.CategoryForm;
import frost.countermobile.forum.Model.Category;
import frost.countermobile.forum.Model.User;
import frost.countermobile.forum.Service.CategoryService;
import frost.countermobile.forum.Service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    CategoryService categoryService;
    PermissionService permissionService;

    public CategoryController(CategoryService categoryService,
                              PermissionService permissionService) {
        this.categoryService = categoryService;
        this.permissionService = permissionService;
    }

    @GetMapping("/categories")
    @CrossOrigin
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/categories")
    @CrossOrigin
    public Category createCategory(@RequestBody CategoryForm categoryForm) {
        return categoryService.createCategory(categoryForm.getTitle(), categoryForm.getDescription());
    }

    @GetMapping("/categories/{slug}")
    @CrossOrigin
    public Category getCategoryFromTopic(@PathVariable String slug) {
        return categoryService.getCategoryBySlug(slug);
    }

    @DeleteMapping("/categories/{slug}")
    @CrossOrigin
    public Object deleteCategory(@PathVariable String slug,
                                 HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        Category category = categoryService.getCategoryBySlug(slug);
        try {
            if(permissionService.verifyDeletePermission(user, "category", category)){
                categoryService.deleteCategory(category.getId());
                return true;
            }
        } catch (UnathorizedDeleteException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return error;
        }
        return true;
    }

    @PutMapping("/categories/{slug}")
    @CrossOrigin
    public Category modifyCategory(@PathVariable String slug,
                                   @RequestBody CategoryForm categoryForm) {
        Category category = categoryService.getCategoryBySlug(slug);
        categoryService.modifyCategory(categoryForm.getTitle(), categoryForm.getDescription(), category.getId());
        category = categoryService.getCategoryBySlug(slug);
        return category;
    }
}
