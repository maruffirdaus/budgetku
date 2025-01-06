package app.budgetku.ui.shared.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.budgetku.data.database.entity.Category;
import app.budgetku.databinding.RvCategoriesBinding;
import app.budgetku.ui.util.ThemeUtil;

public class CategoriesAdapter extends ListAdapter<Category, CategoriesAdapter.CategoryViewHolder> {
    private OnItemClickCallback onItemClickCallback;
    private List<Integer> selectedCategoryIds = Collections.emptyList();

    public CategoriesAdapter() {
        super(new CategoryDiffCallback());
    }

    public CategoriesAdapter(OnItemClickCallback onItemClickCallback) {
        super(new CategoryDiffCallback());
        this.onItemClickCallback = onItemClickCallback;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final RvCategoriesBinding binding;
        private final OnItemClickCallback onItemClickCallback;

        public CategoryViewHolder(RvCategoriesBinding binding,
                                  OnItemClickCallback onItemClickCallback) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickCallback = onItemClickCallback;
        }

        public void bind(Category category, List<Integer> selectedCategoryIds) {
            if (selectedCategoryIds.contains(category.getId())) {
                int secondaryContainerColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(),
                        com.google.android.material.R.attr.colorSecondaryContainer);
                int onSecondaryContainerColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(),
                        com.google.android.material.R.attr.colorOnSecondaryContainer);
                binding.cardCategory.setCardBackgroundColor(secondaryContainerColor);
                binding.tvCategoryName.setTextColor(onSecondaryContainerColor);
            } else {
                int surfaceContainerHighColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(),
                        com.google.android.material.R.attr.colorSurfaceContainerHigh);
                int onSurfaceVariantColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(),
                        com.google.android.material.R.attr.colorOnSurfaceVariant);
                binding.cardCategory.setCardBackgroundColor(surfaceContainerHighColor);
                binding.tvCategoryName.setTextColor(onSurfaceVariantColor);
            }
            binding.tvCategoryName.setText(category.getName());
            binding.cardCategory.setOnClickListener(v -> onItemClickCallback.onItemClick(category));
            binding.cardCategory.setOnLongClickListener(v -> {
                onItemClickCallback.onItemLongClick(category);
                return true;
            });
        }
    }

    static class CategoryDiffCallback extends DiffUtil.ItemCallback<Category> {

        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public interface OnItemClickCallback {
        void onItemClick(Category category);
        void onItemLongClick(Category category);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvCategoriesBinding binding = RvCategoriesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding, onItemClickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getItem(position);
        holder.bind(category, selectedCategoryIds);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setSelectedCategoryIds(List<Integer> selectedCategoryIds) {
        List<Integer> unselectedIds = new ArrayList<>(this.selectedCategoryIds);
        unselectedIds.removeAll(selectedCategoryIds);
        List<Integer> newlySelectedIds = new ArrayList<>(selectedCategoryIds);
        newlySelectedIds.removeAll(this.selectedCategoryIds);
        List<Integer> changedIds = new ArrayList<>(unselectedIds);
        changedIds.addAll(newlySelectedIds);
        List<Integer> changedItemIndex = new ArrayList<>();
        for (int i = 0; i < getCurrentList().size(); i++) {
            if (changedIds.contains(getCurrentList().get(i).getId())) {
                changedItemIndex.add(i);
            }
        }
        this.selectedCategoryIds = new ArrayList<>(selectedCategoryIds);
        changedItemIndex.forEach(this::notifyItemChanged);
    }
}