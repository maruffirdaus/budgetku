package app.budgetku.domain.usecase.wallet;

import app.budgetku.data.database.entity.Wallet;
import app.budgetku.data.repository.WalletsRepository;
import app.budgetku.domain.usecase.base.EditUseCase;

public class EditWalletUseCase extends EditUseCase<Wallet> {
    private final WalletsRepository repository;

    public EditWalletUseCase(WalletsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Wallet data) throws Exception {
        if (data.getName().isBlank()) {
            throw new Exception("Wallet name cannot be blank");
        } else if (data.getCurrency().isBlank()) {
            throw new Exception("Wallet currency cannot be blank");
        } else {
            repository.addWallet(data);
        }
    }
}