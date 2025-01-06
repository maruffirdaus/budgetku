package app.budgetku.ui.shared.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import app.budgetku.data.database.entity.Wallet;
import app.budgetku.domain.usecase.base.AddUseCase;
import app.budgetku.domain.usecase.base.DeleteUseCase;
import app.budgetku.domain.usecase.base.EditUseCase;
import app.budgetku.domain.usecase.base.GetUseCase;
import app.budgetku.domain.usecase.category.DeleteCategoryUseCase;
import app.budgetku.domain.usecase.category.EditCategoryUseCase;
import app.budgetku.domain.usecase.category.GetCategoriesUseCase;
import app.budgetku.domain.usecase.wallet.DeleteWalletUseCase;
import app.budgetku.domain.usecase.wallet.EditWalletUseCase;
import app.budgetku.domain.usecase.wallet.GetWalletsUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WalletsViewModel extends ViewModel {
    private final MutableLiveData<List<Wallet>> _wallets = new MutableLiveData<>();
    public LiveData<List<Wallet>> wallets = _wallets;
    private final MutableLiveData<Wallet> _selectedWallet = new MutableLiveData<>();
    public LiveData<Wallet> selectedWallet = _selectedWallet;
    private final MutableLiveData<String> _walletRelatedMessage = new MutableLiveData<>();
    public LiveData<String> walletRelatedMessage = _walletRelatedMessage;
    private GetUseCase<List<Wallet>> getWallets;
    private final AddUseCase<Wallet> addWallet;
    private EditUseCase<Wallet> editWallet;
    private DeleteUseCase<Wallet> deleteWallet;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Inject
    public WalletsViewModel(GetUseCase<List<Wallet>> getWallets,AddUseCase<Wallet> addWallet,
                            EditUseCase<Wallet> editWallet, DeleteUseCase<Wallet> deleteWallet) {
        this.getWallets = getWallets;
        this.addWallet = addWallet;
        this.editWallet = editWallet;
        this.deleteWallet = deleteWallet;

        getWallets();
    }

    public void setSelectedWallet(Wallet selectedWallet) {
        _selectedWallet.setValue(selectedWallet);
    }

    public void setSelectedWallet(int selectedWallet) {
    }

    public void setWalletRelatedMessage(String walletRelatedMessage) {
        _walletRelatedMessage.setValue(walletRelatedMessage);
    }

    public void getWallets() {
        executorService.execute(()->{
            GetWalletsUseCase get = (GetWalletsUseCase) getWallets;
            _wallets.postValue(get.execute());
        });
    }

    public void addWallet(Wallet wallet) {
        try {
            addWallet.execute(wallet);
            getWallets();
        } catch (Exception e) {
            setWalletRelatedMessage(e.getMessage());
        } finally {
            getWallets();
        }
    }

    public void editWallet(Wallet wallet) {
        executorService.execute(()->{
            EditWalletUseCase edit = (EditWalletUseCase) editWallet;
            try {
                edit.execute(wallet);
            } catch (Exception e) {
                _walletRelatedMessage.postValue(e.getMessage());
            } finally {
                getWallets();
            }
        });
    }

    public void deleteWallet(Wallet wallet) {
        executorService.execute(()->{
            DeleteWalletUseCase delete = (DeleteWalletUseCase) deleteWallet;
            delete.execute(wallet);
            getWallets();
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}