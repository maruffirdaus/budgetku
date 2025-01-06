package app.budgetku.domain.usecase.wallet;

import app.budgetku.data.database.entity.Wallet;
import app.budgetku.data.repository.WalletsRepository;
import app.budgetku.domain.usecase.base.DeleteUseCase;

public class DeleteWalletUseCase extends DeleteUseCase<Wallet> {
    private final WalletsRepository repository;

    public DeleteWalletUseCase(WalletsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Wallet data) {
        repository.deleteWallet(data);
    }
}