package app.budgetku.domain.usecase.base;

public abstract class DeleteUseCase<T> {

    public abstract void execute(T data) throws Exception;
}