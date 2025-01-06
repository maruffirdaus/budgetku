package app.budgetku.domain.usecase.transaction;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.Wallet;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.data.repository.WalletsRepository;
import app.budgetku.domain.usecase.base.DeleteUseCase;

public class DeleteTransactionUseCase extends DeleteUseCase<Transaction> {
    private final TransactionsRepository transactionsRepository;
    private final WalletsRepository walletsRepository;

    public DeleteTransactionUseCase(TransactionsRepository transactionsRepository,
                                 WalletsRepository walletsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.walletsRepository = walletsRepository;
    }

    @Override
    public void execute(Transaction data) {
        Wallet currentWallet = walletsRepository.getWallet(data.getWalletId());
        if (data.getIsIncome()) {
            currentWallet.setBalance(currentWallet.getBalance() - data.getAmount());
        } else {
            currentWallet.setBalance(currentWallet.getBalance() + data.getAmount());
        }
        walletsRepository.editWallet(currentWallet);
        transactionsRepository.deleteTransaction(data);
    }
}