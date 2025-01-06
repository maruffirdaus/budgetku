package app.budgetku.domain.usecase.base;

public abstract class EditUseCase<T> {

    public abstract void execute(T data) throws Exception;
}