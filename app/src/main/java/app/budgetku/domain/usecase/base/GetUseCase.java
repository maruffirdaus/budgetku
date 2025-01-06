package app.budgetku.domain.usecase.base;

public abstract class GetUseCase<T> {

    public abstract T execute() throws Exception;
}