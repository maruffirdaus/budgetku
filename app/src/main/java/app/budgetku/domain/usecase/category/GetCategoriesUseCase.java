package app.budgetku.domain.usecase.category;

import java.util.List;

import app.budgetku.data.database.entity.Category;
import app.budgetku.data.repository.CategoriesRepository;
import app.budgetku.domain.usecase.base.GetUseCase;

public class GetCategoriesUseCase extends GetUseCase<List<Category>> {
    private final CategoriesRepository repository;

    public GetCategoriesUseCase(CategoriesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> execute() {
        return repository.getCategories();
    }
}