package app.budgetku.domain.usecase.base;

public abstract class GetBy1ParamUseCase<T, U> {

    public abstract U execute(T data) throws Exception;
}