package app.budgetku.domain.usecase.category;

import app.budgetku.data.database.entity.Category;
import app.budgetku.data.repository.CategoriesRepository;
import app.budgetku.domain.usecase.base.DeleteUseCase;

public class DeleteCategoryUseCase extends DeleteUseCase<Category> {
    private final CategoriesRepository repository;

    public DeleteCategoryUseCase(CategoriesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Category data) {
        repository.deleteCategory(data);
    }
}