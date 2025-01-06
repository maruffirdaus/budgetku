package app.budgetku.domain.usecase.base;

public abstract class GetBy2ParamsUseCase<T, U, V> {

    public abstract V execute(T param1, U param2) throws Exception;
}