package app.budgetku.ui.shared.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import app.budgetku.data.database.entity.Wallet;
import app.budgetku.domain.usecase.base.AddUseCase;
import app.budgetku.domain.usecase.base.DeleteUseCase;
import app.budgetku.domain.usecase.base.EditUseCase;
import app.budgetku.domain.usecase.base.GetUseCase;
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

    @Inject
    public WalletsViewModel(AddUseCase<Wallet> addWallet) {
        this.addWallet = addWallet;
        _wallets.setValue(List.of(new Wallet(0, "Paypal", "$", 5000)));
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

    }

    public void addWallet(Wallet wallet) {
        try {
            addWallet.execute(wallet);
            getWallets();
        } catch (Exception e) {
            setWalletRelatedMessage(e.getMessage());
        }
    }

    public void editWallet(Wallet wallet) {
    }

    public void deleteWallet(Wallet wallet) {
    }
}