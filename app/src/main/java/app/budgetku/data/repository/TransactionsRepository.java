package app.budgetku.data.repository;

import java.util.List;

import app.budgetku.data.database.dao.TransactionCategoryCrossRefDao;
import app.budgetku.data.database.dao.TransactionDao;
import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.TransactionCategoryCrossRef;
import app.budgetku.data.database.entity.TransactionWithCategories;

public class TransactionsRepository {
    private final TransactionDao transactionDao;
    private final TransactionCategoryCrossRefDao transactionCategoryCrossRefDao;

    public TransactionsRepository(TransactionDao transactionDao,
                                  TransactionCategoryCrossRefDao transactionCategoryCrossRefDao) {
        this.transactionDao = transactionDao;
        this.transactionCategoryCrossRefDao = transactionCategoryCrossRefDao;
    }

    public List<Transaction> getTransactions(int walletId, String date) {
        return transactionDao.getTransactions(walletId, date);
    }

    public List<Transaction> getTransactions(int walletId, int categoryId, String date) {
        return transactionDao.getTransactions(walletId, categoryId, date);
    }

    public Transaction getTransaction(int id) {
        return transactionDao.getTransaction(id);
    }

    public TransactionWithCategories getTransactionWithCategories(int id) {
        return transactionDao.getTransactionWithCategories(id);
    }

    public void addTransaction(Transaction transaction, List<Integer> categoryIds) {
        long transactionId = transactionDao.insert(transaction);
        categoryIds.forEach(categoryId -> transactionCategoryCrossRefDao
                .insert(new TransactionCategoryCrossRef((int) transactionId, categoryId))
        );
    }

    public void editTransaction(Transaction transaction, List<Integer> categoryIds) {
        List<TransactionCategoryCrossRef> oldRefs =
                transactionCategoryCrossRefDao.getTransactionCategoryCrossRefs(transaction.getId());
        oldRefs.forEach(transactionCategoryCrossRefDao::delete);
        transactionDao.update(transaction);
        categoryIds.forEach(categoryId -> transactionCategoryCrossRefDao
                .insert(new TransactionCategoryCrossRef(transaction.getId(), categoryId))
        );
    }

    public void deleteTransaction(Transaction transaction) {
        transactionDao.delete(transaction);
    }
}