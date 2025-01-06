package app.budgetku.domain.usecase.transaction;

import java.util.List;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.Wallet;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.data.repository.WalletsRepository;
import app.budgetku.domain.usecase.base.AddWith2ParamsUseCase;

public class AddTransactionUseCase extends AddWith2ParamsUseCase<Transaction, List<Integer>> {
    private final TransactionsRepository transactionsRepository;
    private final WalletsRepository walletsRepository;

    public AddTransactionUseCase(TransactionsRepository transactionsRepository,
                                 WalletsRepository walletsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.walletsRepository = walletsRepository;
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
            Wallet currentWallet = walletsRepository.getWallet(data1.getWalletId());
            if (data1.getIsIncome()) {
                currentWallet.setBalance(currentWallet.getBalance() + data1.getAmount());
            } else {
                currentWallet.setBalance(currentWallet.getBalance() - data1.getAmount());
            }
            walletsRepository.editWallet(currentWallet);
            transactionsRepository.addTransaction(data1, data2);
        }
    }
}