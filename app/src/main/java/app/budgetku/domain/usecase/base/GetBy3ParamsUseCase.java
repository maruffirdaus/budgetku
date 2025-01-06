package app.budgetku.domain.usecase.base;

import java.util.List;

import app.budgetku.data.database.entity.Transaction;

public abstract class GetBy3ParamsUseCase<T, U, V, W> {

    public abstract W execute(T param1, U param2, V param3) throws Exception;
}