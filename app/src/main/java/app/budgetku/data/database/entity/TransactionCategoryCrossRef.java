package app.budgetku.data.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {"transactionId", "categoryId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Transaction.class,
                        parentColumns = "id",
                        childColumns = "transactionId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "id",
                        childColumns = "categoryId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
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