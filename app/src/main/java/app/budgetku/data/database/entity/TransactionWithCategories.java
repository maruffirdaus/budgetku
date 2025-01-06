package app.budgetku.data.database.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TransactionWithCategories {
    @Embedded
    private Transaction transaction;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = TransactionCategoryCrossRef.class,
                    parentColumn = "transactionId",
                    entityColumn = "categoryId"
            )
    )
    private List<Category> categories;

    public TransactionWithCategories(Transaction transaction, List<Category> categories) {
        this.transaction = transaction;
        this.categories = categories;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
