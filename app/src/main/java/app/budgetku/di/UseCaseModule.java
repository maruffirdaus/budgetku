package app.budgetku.di;

import java.util.List;

import javax.inject.Singleton;

import app.budgetku.data.database.entity.Category;
import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.TransactionWithCategories;
import app.budgetku.data.database.entity.Wallet;
import app.budgetku.data.repository.CategoriesRepository;
import app.budgetku.data.repository.TransactionsRepository;
import app.budgetku.data.repository.WalletsRepository;
import app.budgetku.domain.usecase.base.AddUseCase;
import app.budgetku.domain.usecase.base.AddWith2ParamsUseCase;
import app.budgetku.domain.usecase.base.DeleteUseCase;
import app.budgetku.domain.usecase.base.EditUseCase;
import app.budgetku.domain.usecase.base.EditWith2ParamsUseCase;
import app.budgetku.domain.usecase.base.GetBy1ParamUseCase;
import app.budgetku.domain.usecase.base.GetBy2ParamsUseCase;
import app.budgetku.domain.usecase.base.GetBy3ParamsUseCase;
import app.budgetku.domain.usecase.base.GetUseCase;
import app.budgetku.domain.usecase.category.AddCategoryUseCase;
import app.budgetku.domain.usecase.category.DeleteCategoryUseCase;
import app.budgetku.domain.usecase.category.EditCategoryUseCase;
import app.budgetku.domain.usecase.category.GetCategoriesUseCase;
import app.budgetku.domain.usecase.transaction.AddTransactionUseCase;
import app.budgetku.domain.usecase.transaction.DeleteTransactionUseCase;
import app.budgetku.domain.usecase.transaction.EditTransactionUseCase;
import app.budgetku.domain.usecase.transaction.GetTransactionWithCategoriesUseCase;
import app.budgetku.domain.usecase.transaction.GetTransactionsByWalletCategoryDateUseCase;
import app.budgetku.domain.usecase.transaction.GetTransactionsByWalletDateUseCase;
import app.budgetku.domain.usecase.wallet.AddWalletUseCase;
import app.budgetku.domain.usecase.wallet.DeleteWalletUseCase;
import app.budgetku.domain.usecase.wallet.EditWalletUseCase;
import app.budgetku.domain.usecase.wallet.GetWalletByIdUseCase;
import app.budgetku.domain.usecase.wallet.GetWalletsUseCase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class UseCaseModule {

    @Provides
    @Singleton
    public static GetUseCase<List<Wallet>> provideGetWalletsUseCase(WalletsRepository repository) {
        return new GetWalletsUseCase(repository);
    }

    @Provides
    @Singleton
    public static GetBy1ParamUseCase<Integer, Wallet> provideGetWalletByIdUseCase(WalletsRepository repository) {
        return new GetWalletByIdUseCase(repository);
    }

    @Provides
    @Singleton
    public static AddUseCase<Wallet> provideAddWalletUseCase(WalletsRepository repository) {
        return new AddWalletUseCase(repository);
    }

    @Provides
    @Singleton
    public static EditUseCase<Wallet> provideEditWalletUseCase(WalletsRepository repository) {
        return new EditWalletUseCase(repository);
    }

    @Provides
    @Singleton
    public static DeleteUseCase<Wallet> provideDeleteWalletUseCase(WalletsRepository repository) {
        return new DeleteWalletUseCase(repository);
    }

    @Provides
    @Singleton
    public static GetUseCase<List<Category>> provideGetCategoriesUseCase(CategoriesRepository repository) {
        return new GetCategoriesUseCase(repository);
    }

    @Provides
    @Singleton
    public static AddUseCase<Category> provideAddCategoryUseCase(CategoriesRepository repository) {
        return new AddCategoryUseCase(repository);
    }

    @Provides
    @Singleton
    public static EditUseCase<Category> provideEditCategoryUseCase(CategoriesRepository repository) {
        return new EditCategoryUseCase(repository);
    }

    @Provides
    @Singleton
    public static DeleteUseCase<Category> provideDeleteCategoryUseCase(CategoriesRepository repository) {
        return new DeleteCategoryUseCase(repository);
    }

    @Provides
    public static GetBy1ParamUseCase<Integer,
            TransactionWithCategories> provideGetTransactionWithCategories(TransactionsRepository repository) {
        return new GetTransactionWithCategoriesUseCase(repository);
    }

    @Provides
    @Singleton
    public static GetBy2ParamsUseCase<Integer, String,
            List<Transaction>> provideGetTransactionsByWalletDateUseCase(TransactionsRepository repository) {
        return new GetTransactionsByWalletDateUseCase(repository);
    }

    @Provides
    @Singleton
    public static GetBy3ParamsUseCase<Integer, Integer, String,
            List<Transaction>> provideGetTransactionsByWalletCategoryDateUseCase(TransactionsRepository repository) {
        return new GetTransactionsByWalletCategoryDateUseCase(repository);
    }

    @Provides
    @Singleton
    public static AddWith2ParamsUseCase<Transaction, List<Integer>> provideAddTransaction(TransactionsRepository transactionsRepository,
                                                                                          WalletsRepository walletsRepository) {
        return new AddTransactionUseCase(transactionsRepository, walletsRepository);
    }

    @Provides
    @Singleton
    public static EditWith2ParamsUseCase<Transaction, List<Integer>> provideEditTransaction(TransactionsRepository transactionsRepository,
                                                                                            WalletsRepository walletsRepository) {
        return new EditTransactionUseCase(transactionsRepository, walletsRepository);
    }

    @Provides
    @Singleton
    public static DeleteUseCase<Transaction> provideDeleteTransaction(TransactionsRepository transactionsRepository,
                                                                      WalletsRepository walletsRepository) {
        return new DeleteTransactionUseCase(transactionsRepository, walletsRepository);
    }
}