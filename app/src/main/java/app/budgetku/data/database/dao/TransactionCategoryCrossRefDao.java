package app.budgetku.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import app.budgetku.data.database.entity.TransactionCategoryCrossRef;

@Dao
public interface TransactionCategoryCrossRefDao {

    @Insert
    void insert(TransactionCategoryCrossRef transactionCategoryCrossRef);

    @Query("SELECT * FROM transactioncategorycrossref WHERE transactionId = :transactionId")
    List<TransactionCategoryCrossRef> getTransactionCategoryCrossRefs(int transactionId);

    @Delete
    void delete(TransactionCategoryCrossRef transactionCategoryCrossRef);
}