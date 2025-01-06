package app.budgetku.ui.shared.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import app.budgetku.data.database.entity.Category;
import app.budgetku.domain.usecase.base.AddUseCase;
import app.budgetku.domain.usecase.base.DeleteUseCase;
import app.budgetku.domain.usecase.base.EditUseCase;
import app.budgetku.domain.usecase.base.GetUseCase;
import app.budgetku.domain.usecase.category.AddCategoryUseCase;
import app.budgetku.domain.usecase.category.DeleteCategoryUseCase;
import app.budgetku.domain.usecase.category.EditCategoryUseCase;
import app.budgetku.domain.usecase.category.GetCategoriesUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoriesViewModel extends ViewModel {
    public static final int UNUSED_CATEGORY_ID = 0;
    private final MutableLiveData<List<Category>> _categories = new MutableLiveData<>();
    public LiveData<List<Category>> categories = _categories;
    private final MutableLiveData<Integer> _selectedCategoryId = new MutableLiveData<>(0);
    public LiveData<Integer> selectedCategoryId = _selectedCategoryId;
    private final MutableLiveData<String> _categoryRelatedMessage = new MutableLiveData<>();
    public LiveData<String> categoryRelatedMessage = _categoryRelatedMessage;
    private final GetUseCase<List<Category>> getCategories;
    private final AddUseCase<Category> addCategory;
    private final EditUseCase<Category> editCategory;
    private final DeleteUseCase<Category> deleteCategory;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Inject
    public CategoriesViewModel(GetUseCase<List<Category>> getCategories,
                               AddUseCase<Category> addCategory, EditUseCase<Category> editCategory,
                               DeleteUseCase<Category> deleteCategory) {
        this.getCategories = getCategories;
        this.addCategory = addCategory;
        this.editCategory = editCategory;
        this.deleteCategory = deleteCategory;
        getCategories();
    }

    public void setSelectedCategoryId(Integer selectedCategoryId) {
        _selectedCategoryId.setValue(selectedCategoryId);
    }

    public void setCategoryRelatedMessage(String categoryRelatedMessage) {
        _categoryRelatedMessage.setValue(categoryRelatedMessage);
    }

    public void getCategories() {
        executorService.execute(()->{
            GetCategoriesUseCase get = (GetCategoriesUseCase) getCategories;
            _categories.postValue(get.execute());
        });
    }

    public void addCategory(Category category) {
        executorService.execute(()->{
            AddCategoryUseCase add = (AddCategoryUseCase) addCategory;
            try {
                add.execute(category);
            } catch (Exception e) {
                _categoryRelatedMessage.postValue(e.getMessage());
            } finally {
                getCategories();
            }
        });
    }

    public void editCategory(Category category) {
        executorService.execute(()->{
            EditCategoryUseCase edit = (EditCategoryUseCase) editCategory;
            try {
                edit.execute(category);
            } catch (Exception e) {
                _categoryRelatedMessage.postValue(e.getMessage());
            } finally {
                getCategories();
            }
        });
    }

    public void deleteCategory(Category category) {
        executorService.execute(()->{
            DeleteCategoryUseCase delete = (DeleteCategoryUseCase) deleteCategory;
            delete.execute(category);
            getCategories();
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}