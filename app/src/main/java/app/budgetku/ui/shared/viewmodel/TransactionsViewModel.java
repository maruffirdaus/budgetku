package app.budgetku.ui.shared.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.TransactionWithCategories;
import app.budgetku.domain.usecase.base.AddWith2ParamsUseCase;
import app.budgetku.domain.usecase.base.DeleteUseCase;
import app.budgetku.domain.usecase.base.EditWith2ParamsUseCase;
import app.budgetku.domain.usecase.base.GetBy1ParamsUseCase;
import app.budgetku.domain.usecase.base.GetBy2ParamsUseCase;
import app.budgetku.domain.usecase.base.GetBy3ParamsUseCase;
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
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TransactionsViewModel extends ViewModel {
    private final MutableLiveData<List<Transaction>> _transactions = new MutableLiveData<>();
    public LiveData<List<Transaction>> transactions = _transactions;
    private final MutableLiveData<TransactionWithCategories> _transactionWithCategories = new MutableLiveData<>();
    public LiveData<TransactionWithCategories> transactionWithCategories = _transactionWithCategories;
    private int selectedWalletId;
    private int selectedCategoryId;
    private final MutableLiveData<Integer> _selectedYear =
            new MutableLiveData<>(LocalDate.now().getYear());
    public LiveData<Integer> selectedYear = _selectedYear;
    private final MutableLiveData<Integer> _selectedMonth = new MutableLiveData<>(0);
    public LiveData<Integer> selectedMonth = _selectedMonth;
    private boolean isFilteredTransactionsEnabled;
    private final MutableLiveData<String> _transactionRelatedMessage =
            new MutableLiveData<>();
    public LiveData<String> transactionRelatedMessage = _transactionRelatedMessage;
    private final GetBy1ParamsUseCase<Integer,
            TransactionWithCategories> getTransactionWithCategories;
    private final GetBy2ParamsUseCase<Integer, String,
            List<Transaction>> getTransactionsByWalletDate;
    private final GetBy3ParamsUseCase<Integer, Integer, String,
            List<Transaction>> getTransactionsByWalletCategoryDate;
    private AddWith2ParamsUseCase<Transaction, List<Integer>> addTransaction;
    private EditWith2ParamsUseCase<Transaction, List<Integer>> editTransaction;
    private DeleteUseCase<Transaction> deleteTransaction;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Inject
    public TransactionsViewModel(GetBy1ParamsUseCase<Integer,
            TransactionWithCategories> getTransactionWithCategories, GetBy2ParamsUseCase<Integer,
            String, List<Transaction>> getTransactionsByWalletDate, GetBy3ParamsUseCase<Integer,
            Integer, String, List<Transaction>> getTransactionsByWalletCategoryDate) {
        this.getTransactionWithCategories = getTransactionWithCategories;
        this.getTransactionsByWalletDate = getTransactionsByWalletDate;
        this.getTransactionsByWalletCategoryDate = getTransactionsByWalletCategoryDate;
        getTransactions();
    }

    public void setSelectedWalletId(int selectedWalletId) {
        this.selectedWalletId = selectedWalletId;
    }

    public void setSelectedCategoryId(int selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    public void setSelectedYear(int selectedYear) {
        _selectedYear.setValue(selectedYear);
    }

    public void setSelectedMonth(int selectedMonth) {
        _selectedMonth.setValue(selectedMonth);
    }

    public void setFilteredTransactionsEnabled(boolean isFilteredTransactionsEnabled) {
        this.isFilteredTransactionsEnabled = isFilteredTransactionsEnabled;
    }

    public void setTransactionRelatedMessage(String transactionRelatedMessage) {
        _transactionRelatedMessage.setValue(transactionRelatedMessage);
    }

    public void getTransactionWithCategories(int id) {
        executorService.execute(() -> {
            GetTransactionWithCategoriesUseCase get =
                    (GetTransactionWithCategoriesUseCase) getTransactionWithCategories;
            _transactionWithCategories.postValue(get.execute(id));
        });
    }

    public void getTransactions() {
        _transactions.setValue(List.of(
                new Transaction(0, 0, "Apple Music", 59000, false, "2024-02-11"),
                new Transaction(0, 0, "Google Play", 19000, false, "2024-02-11"),
                new Transaction(0, 0, "YouTube", 49000, false, "2024-02-11"),
                new Transaction(0, 0, "Salary", 50000, true, "2024-02-11")
        ));
        /*executorService.execute(() -> {
            GetTransactionsByWalletDateUseCase getByWalletDate =
                    (GetTransactionsByWalletDateUseCase) getTransactionsByWalletDate;
            GetTransactionsByWalletCategoryDateUseCase getByWalletCategoryDate =
                    (GetTransactionsByWalletCategoryDateUseCase) getTransactionsByWalletCategoryDate;
            if (isFilteredTransactionsEnabled) {
                if (selectedCategoryId != 0) {
                    if (selectedMonth.getValue() != null && selectedMonth.getValue() != 0) {
                        _transactions.postValue(getByWalletCategoryDate.execute(selectedWalletId,
                                selectedCategoryId, selectedYear + "-" + selectedMonth + "%"));
                    } else {
                        _transactions.postValue(getByWalletCategoryDate.execute(selectedWalletId,
                                selectedCategoryId, selectedYear + "%"));
                    }
                } else if (selectedMonth.getValue() != null && selectedMonth.getValue() != 0) {
                    _transactions.postValue(getByWalletDate.execute(selectedWalletId,
                            selectedYear + "-" + selectedMonth + "%"));
                } else {
                    _transactions.postValue(getByWalletDate.execute(selectedWalletId,
                            selectedYear + "%"));
                }
            } else {
                _transactions.postValue(getByWalletDate.execute(selectedWalletId,
                        selectedYear + "%"));
            }
        });*/
    }

    public void addTransaction(Transaction transaction, List<Integer> selectedCategoryIds) {
        executorService.execute(()->{
            AddTransactionUseCase add = (AddTransactionUseCase) addTransaction;
            try {
                add.execute(transaction,selectedCategoryIds);
            } catch (Exception e) {
                _transactionRelatedMessage.postValue(e.getMessage());
            } finally {
                getTransactions();
            }
        });
    }

    public void editTransaction(Transaction transaction, List<Integer> selectedCategoryIds) {
        executorService.execute(()->{
            EditTransactionUseCase edit = (EditTransactionUseCase) editTransaction;
            try {
                edit.execute(transaction,selectedCategoryIds);
            } catch (Exception e) {
                _transactionRelatedMessage.postValue(e.getMessage());
            } finally {
                getTransactions();
            }
        });
    }

    public void deleteTransaction(Transaction transaction) {
        executorService.execute(()->{
            DeleteTransactionUseCase delete = (DeleteTransactionUseCase) deleteTransaction;
            delete.execute(transaction);
            getTransactions();
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}