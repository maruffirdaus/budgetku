package app.budgetku.domain.usecase.transaction;

import java.util.List;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.domain.usecase.base.EditWith2ParamsUseCase;

public class EditTransactionUseCase extends EditWith2ParamsUseCase<Transaction, List<Integer>> {
    private final TransactionsRepository repository;

    public EditTransactionUseCase(TransactionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Transaction data1, List<Integer> data2) throws Exception {
        if (data1.getTitle().isBlank()) {
            throw new Exception("Transaction title cannot be blank");
        } else if (data1.getAmount() == 0) {
            throw new Exception("Transaction amount cannot be 0");
        } else if (data1.getDate().isBlank()) {
            throw new Exception("Transaction date cannot be blank");
        } else {
            repository.addTransaction(data1, data2);
        }
    }
}