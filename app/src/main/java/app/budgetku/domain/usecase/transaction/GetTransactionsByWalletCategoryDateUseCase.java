package app.budgetku.domain.usecase.transaction;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.domain.usecase.base.GetBy3ParamsUseCase;

public class GetTransactionsByWalletCategoryDateUseCase extends GetBy3ParamsUseCase<Integer,
        Integer, String, List<Transaction>> {
    private final TransactionsRepository repository;

    public GetTransactionsByWalletCategoryDateUseCase(TransactionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transaction> execute(Integer param1, Integer param2, String param3) {
        return repository.getTransactions(param1, param2, param3);
    }
}