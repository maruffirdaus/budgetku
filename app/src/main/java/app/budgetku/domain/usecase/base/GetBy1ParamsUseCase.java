package app.budgetku.domain.usecase.base;

public abstract class GetBy1ParamsUseCase<T, U> {

    public abstract U execute(T data) throws Exception;
}