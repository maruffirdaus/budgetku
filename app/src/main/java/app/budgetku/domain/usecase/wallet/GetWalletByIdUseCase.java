package app.budgetku.domain.usecase.wallet;

import app.budgetku.data.database.entity.Wallet;
import app.budgetku.data.repository.WalletsRepository;
import app.budgetku.domain.usecase.base.GetBy1ParamUseCase;

public class GetWalletByIdUseCase extends GetBy1ParamUseCase<Integer, Wallet> {
    private final WalletsRepository repository;

    public GetWalletByIdUseCase(WalletsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet execute(Integer data) {
        return repository.getWallet(data);
    }
}
