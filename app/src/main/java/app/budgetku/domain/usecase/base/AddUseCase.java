package app.budgetku.domain.usecase.base;

public abstract class AddUseCase<T> {

    public abstract void execute(T data) throws Exception;
}