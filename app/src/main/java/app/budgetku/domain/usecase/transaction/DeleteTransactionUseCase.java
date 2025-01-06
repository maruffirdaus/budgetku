package app.budgetku.domain.usecase.transaction;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.domain.usecase.base.DeleteUseCase;

public class DeleteTransactionUseCase extends DeleteUseCase<Transaction> {
    private final TransactionsRepository repository;

    public DeleteTransactionUseCase(TransactionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Transaction data) {
        repository.deleteTransaction(data);
    }
}