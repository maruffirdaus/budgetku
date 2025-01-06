package app.budgetku.data.repository;

import java.util.List;

import app.budgetku.data.database.dao.CategoryDao;
import app.budgetku.data.database.entity.Category;

public class CategoriesRepository {
    private final CategoryDao dao;

    public CategoriesRepository(CategoryDao dao) {
        this.dao = dao;
    }

    public List<Category> getCategories() {
        return dao.getCategories();
    }

    public void addCategory(Category category) {
        dao.insert(category);
    }

    public void editCategory(Category category) {
        dao.update(category);
    }

    public void deleteCategory(Category category) {
        dao.delete(category);
    }
}