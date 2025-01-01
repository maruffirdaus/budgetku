package app.budgetku.data.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"transactionId", "categoryId"})
public class TransactionCategoryCrossRef {
    private int transactionId;
    private int categoryId;

    public TransactionCategoryCrossRef(int transactionId, int categoryId) {
        this.transactionId = transactionId;
        this.categoryId = categoryId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}