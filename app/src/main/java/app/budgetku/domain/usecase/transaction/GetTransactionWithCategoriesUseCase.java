package app.budgetku.domain.usecase.transaction;

import app.budgetku.data.database.entity.TransactionWithCategories;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.domain.usecase.base.GetBy1ParamUseCase;

public class GetTransactionWithCategoriesUseCase extends GetBy1ParamUseCase<Integer, TransactionWithCategories> {
    private final TransactionsRepository repository;

    public GetTransactionWithCategoriesUseCase(TransactionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionWithCategories execute(Integer data) {
        return repository.getTransactionWithCategories(data);
    }
}