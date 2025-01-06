package app.budgetku.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.budgetku.data.database.dao.CategoryDao;
import app.budgetku.data.database.dao.TransactionCategoryCrossRefDao;
import app.budgetku.data.database.dao.TransactionDao;
import app.budgetku.data.database.dao.WalletDao;
import app.budgetku.data.database.entity.Category;
import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.TransactionCategoryCrossRef;
import app.budgetku.data.database.entity.Wallet;

@Database(entities = {Wallet.class, Category.class, Transaction.class,
        TransactionCategoryCrossRef.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WalletDao walletDao();

    public abstract CategoryDao categoryDao();

    public abstract TransactionDao transactionDao();

    public abstract TransactionCategoryCrossRefDao transactionCategoryCrossRefDao();
}