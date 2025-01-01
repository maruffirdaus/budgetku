package app.budgetku.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.budgetku.data.database.dao.CategoryDao;
import app.budgetku.data.database.dao.TransactionDao;
import app.budgetku.data.database.dao.WalletDao;
import app.budgetku.data.database.entity.Category;
import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.TransactionCategoryCrossRef;
import app.budgetku.data.database.entity.RecurringTransaction;
import app.budgetku.data.database.entity.RecurringTransactionCategoryCrossRef;
import app.budgetku.data.database.entity.Wallet;

@Database(entities = {Wallet.class, Category.class, Transaction.class, RecurringTransaction.class, TransactionCategoryCrossRef.class, RecurringTransactionCategoryCrossRef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WalletDao walletDao();
    public abstract CategoryDao categoryDao();
    public abstract TransactionDao transactionDao();
}