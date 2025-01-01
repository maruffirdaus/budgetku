package app.budgetku.data.database.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"recurringTransactionId", "categoryId"})
public class RecurringTransactionCategoryCrossRef {
    private int recurringTransactionId;
    private int categoryId;

    public RecurringTransactionCategoryCrossRef(int recurringTransactionId, int categoryId) {
        this.recurringTransactionId = recurringTransactionId;
        this.categoryId = categoryId;
    }

    public int getRecurringTransactionId() {
        return recurringTransactionId;
    }

    public void setRecurringTransactionId(int recurringTransactionId) {
        this.recurringTransactionId = recurringTransactionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}