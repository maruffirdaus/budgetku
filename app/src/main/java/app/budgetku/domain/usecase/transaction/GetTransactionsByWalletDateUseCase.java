package app.budgetku.domain.usecase.transaction;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.domain.usecase.base.GetBy2ParamsUseCase;

public class GetTransactionsByWalletDateUseCase extends GetBy2ParamsUseCase<Integer, String,
        List<Transaction>> {
    private final TransactionsRepository repository;

    public GetTransactionsByWalletDateUseCase(TransactionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transaction> execute(Integer param1, String param2) {
        return repository.getTransactions(param1, param2);
    }
}