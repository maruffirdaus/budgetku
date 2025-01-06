package app.budgetku.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.TransactionWithCategories;

@Dao
public interface TransactionDao {

    @Insert
    long insert(Transaction transaction);

    @Query("SELECT * FROM `transaction` WHERE walletId = :walletId AND date LIKE :date")
    List<Transaction> getTransactions(int walletId, String date);

    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction` t " +
            "JOIN transactioncategorycrossref c ON t.id = c.transactionId " +
            "WHERE t.walletId = :walletId AND c.categoryId = :categoryId AND t.date LIKE :date")
    List<Transaction> getTransactions(int walletId, int categoryId, String date);

    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction` WHERE id = :id")
    TransactionWithCategories getTransactionWithCategories(int id);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);
}