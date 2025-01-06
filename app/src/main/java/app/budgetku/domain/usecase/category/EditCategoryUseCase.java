package app.budgetku.domain.usecase.category;

import app.budgetku.data.database.entity.Category;
import app.budgetku.data.repository.CategoriesRepository;
import app.budgetku.domain.usecase.base.EditUseCase;

public class EditCategoryUseCase extends EditUseCase<Category> {
    private final CategoriesRepository repository;

    public EditCategoryUseCase(CategoriesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Category data) throws Exception {
        if (data.getName().isBlank()) {
            throw new Exception("Category name cannot be blank");
        } else {
            repository.editCategory(data);
        }
    }
}