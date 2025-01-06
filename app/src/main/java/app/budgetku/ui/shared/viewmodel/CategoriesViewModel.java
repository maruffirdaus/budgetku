package app.budgetku.ui.shared.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import app.budgetku.data.database.entity.Category;
import app.budgetku.domain.usecase.base.AddUseCase;
import app.budgetku.domain.usecase.base.DeleteUseCase;
import app.budgetku.domain.usecase.base.EditUseCase;
import app.budgetku.domain.usecase.base.GetUseCase;
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

    @Inject
    public CategoriesViewModel(GetUseCase<List<Category>> getCategories,
                               AddUseCase<Category> addCategory, EditUseCase<Category> editCategory,
                               DeleteUseCase<Category> deleteCategory) {
        this.getCategories = getCategories;
        this.addCategory = addCategory;
        this.editCategory = editCategory;
        this.deleteCategory = deleteCategory;
    }

    public void setSelectedCategoryId(Integer selectedCategoryId) {
        _selectedCategoryId.setValue(selectedCategoryId);
    }

    public void setCategoryRelatedMessage(String categoryRelatedMessage) {
        _categoryRelatedMessage.setValue(categoryRelatedMessage);
    }

    public void getCategories() {

    }

    public void addCategory(Category category) {
    }

    public void editCategory(Category category) {
    }

    public void deleteCategory(Category category) {
    }
}