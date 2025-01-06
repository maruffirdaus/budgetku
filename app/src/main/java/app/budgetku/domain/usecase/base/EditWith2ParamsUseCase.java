package app.budgetku.domain.usecase.base;

public abstract class EditWith2ParamsUseCase<T, U> {

    public abstract void execute(T data1, U data2) throws Exception;
}