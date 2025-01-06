package app.budgetku.domain.usecase.wallet;

import java.util.Collections;
import java.util.List;

import app.budgetku.data.database.entity.Wallet;
import app.budgetku.data.repository.WalletsRepository;
import app.budgetku.domain.usecase.base.GetUseCase;

public class GetWalletsUseCase extends GetUseCase<List<Wallet>> {
    private final WalletsRepository repository;

    public GetWalletsUseCase(WalletsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Wallet> execute() throws Exception {
        return repository.getWallets();
    }
}