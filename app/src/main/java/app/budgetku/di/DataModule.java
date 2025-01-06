package app.budgetku.di;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import app.budgetku.data.database.AppDatabase;
import app.budgetku.data.database.dao.CategoryDao;
import app.budgetku.data.database.dao.TransactionCategoryCrossRefDao;
import app.budgetku.data.database.dao.TransactionDao;
import app.budgetku.data.database.dao.WalletDao;
import app.budgetku.data.repository.CategoriesRepository;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.data.repository.WalletsRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    public static AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "budgetku_db").build();
    }

    @Provides
    @Singleton
    public static WalletDao provideWalletDao(AppDatabase database) {
        return database.walletDao();
    }

    @Provides
    @Singleton
    public static CategoryDao provideCategoryDao(AppDatabase database) {
        return database.categoryDao();
    }

    @Provides
    @Singleton
    public static TransactionDao provideTransactionDao(AppDatabase database) {
        return database.transactionDao();
    }

    @Provides
    @Singleton
    public static TransactionCategoryCrossRefDao provideTransactionCategoryCrossRefDao(AppDatabase database) {
        return database.transactionCategoryCrossRefDao();
    }

    @Provides
    @Singleton
    public static WalletsRepository provideWalletsRepository(WalletDao dao) {
        return new WalletsRepository(dao);
    }

    @Provides
    @Singleton
    public static CategoriesRepository provideCategoriesRepository(CategoryDao dao) {
        return new CategoriesRepository(dao);
    }

    @Provides
    @Singleton
    public static TransactionsRepository provideTransactionsRepository(TransactionDao transactionDao,
                                                                        TransactionCategoryCrossRefDao transactionCategoryCrossRefDao) {
        return new TransactionsRepository(transactionDao, transactionCategoryCrossRefDao);
    }
}