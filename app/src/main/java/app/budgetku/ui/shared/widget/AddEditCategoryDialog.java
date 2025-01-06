package app.budgetku.ui.shared.widget;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.function.Consumer;

import app.budgetku.R;
import app.budgetku.data.database.entity.Category;
import app.budgetku.databinding.DialogAddEditCategoryBinding;

public class AddEditCategoryDialog extends MaterialAlertDialogBuilder {
    private final DialogAddEditCategoryBinding binding;
    private final Category category;

    public AddEditCategoryDialog(@NonNull Context context, Consumer<Category> onSaveButtonClick) {
        super(context);
        binding = DialogAddEditCategoryBinding.inflate(LayoutInflater.from(context));
        this.category = new Category(0, "");
        setupDialog(onSaveButtonClick);
    }

    public AddEditCategoryDialog(@NonNull Context context, Category category,
                                 Consumer<Category> onSaveButtonClick,
                                 Consumer<Category> onDeleteButtonClick) {
        super(context);
        binding = DialogAddEditCategoryBinding.inflate(LayoutInflater.from(context));
        this.category = category;
        setupEditDialogView();
        setupDialog(onSaveButtonClick, onDeleteButtonClick);
    }

    private void setupEditDialogView() {
        binding.tvTitle.setText(R.string.edit_category);
        if (binding.tfCategoryName.getEditText() != null) {
            binding.tfCategoryName.getEditText().setText(category.getName());
        }
    }

    private void setupDialog(Consumer<Category> onSaveButtonClick) {
        super
                .setView(binding.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    if (binding.tfCategoryName.getEditText() != null) {
                        String categoryName = String
                                .valueOf(binding.tfCategoryName.getEditText().getText());
                        category.setName(categoryName);
                    }
                    onSaveButtonClick.accept(category);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
    }

    private void setupDialog(Consumer<Category> onSaveButtonClick,
                             Consumer<Category> onDeleteButtonClick) {
        super
                .setView(binding.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    if (binding.tfCategoryName.getEditText() != null) {
                        String categoryName = String
                                .valueOf(binding.tfCategoryName.getEditText().getText());
                        category.setName(categoryName);
                    }
                    onSaveButtonClick.accept(category);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setNeutralButton(R.string.delete,
                        (dialog, which) -> onDeleteButtonClick.accept(category));
    }
}
