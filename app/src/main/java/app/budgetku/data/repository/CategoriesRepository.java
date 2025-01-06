package app.budgetku.data.repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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