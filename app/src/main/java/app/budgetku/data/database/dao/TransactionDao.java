package app.budgetku.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import app.budgetku.data.database.entity.RecurringTransaction;
import app.budgetku.data.database.entity.Transaction;

@Dao
public interface TransactionDao {

    @Insert
    void insert(Transaction transaction);

    @Insert
    void insert(RecurringTransaction recurringTransaction);

    @Query("SELECT * FROM `transaction` WHERE walletId = :walletId")
    List<Transaction> getTransactions(int walletId);

    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction` t " +
            "JOIN transactioncategorycrossref c ON t.id = c.transactionId " +
            "WHERE t.walletId = :walletId AND c.categoryId = :categoryId")
    List<Transaction> getTransactions(int walletId, int categoryId);

    @Query("SELECT * FROM recurringtransaction WHERE walletId = :walletId")
    List<RecurringTransaction> getRecurringTransactions(int walletId);

    @androidx.room.Transaction
    @Query("SELECT * FROM recurringtransaction t " +
            "JOIN recurringtransactioncategorycrossref c ON t.id = c.recurringTransactionId " +
            "WHERE t.walletId = :walletId AND c.categoryId = :categoryId")
    List<RecurringTransaction> getRecurringTransactions(int walletId, int categoryId);

    @Update
    void update(Transaction transaction);

    @Update
    void update(RecurringTransaction recurringTransaction);

    @Delete
    void delete(Transaction transaction);

    @Delete
    void delete(RecurringTransaction recurringTransaction);
}