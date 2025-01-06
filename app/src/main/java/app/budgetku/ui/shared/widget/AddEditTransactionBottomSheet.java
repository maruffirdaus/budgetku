package app.budgetku.ui.shared.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import app.budgetku.R;
import app.budgetku.data.database.entity.Category;
import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.TransactionWithCategories;
import app.budgetku.data.database.entity.Wallet;
import app.budgetku.databinding.BottomSheetAddEditTransactionBinding;
import app.budgetku.ui.shared.adapter.CategoriesAdapter;
import app.budgetku.ui.shared.viewmodel.CategoriesViewModel;

public class AddEditTransactionBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetAddEditTransactionBinding binding;
    private final Wallet wallet;
    private final Transaction transaction;
    private final LiveData<List<Category>> categories;
    private final List<Integer> selectedCategoryIds = new ArrayList<>();
    private final Consumer<Category> onCategoryItemLongClick;
    private final Runnable onAddCategoryItemClick;
    private final BiConsumer<Transaction, List<Integer>> onSaveButtonClick;
    private Consumer<Transaction> onDeleteButtonClick;

    public AddEditTransactionBottomSheet(Wallet wallet, LiveData<List<Category>> categories,
                                         Consumer<Category> onCategoryItemLongClick,
                                         Runnable onAddCategoryItemClick,
                                         BiConsumer<Transaction, List<Integer>> onSaveButtonClick) {
        this.wallet = wallet;
        this.transaction = new Transaction(0, wallet.getId(), "", 0, false, "");
        this.categories = categories;
        this.onCategoryItemLongClick = onCategoryItemLongClick;
        this.onAddCategoryItemClick = onAddCategoryItemClick;
        this.onSaveButtonClick = onSaveButtonClick;
    }

    public AddEditTransactionBottomSheet(Wallet wallet, TransactionWithCategories transactionWithCategories, LiveData<List<Category>> categories,
                                         Consumer<Category> onCategoryItemLongClick,
                                         Runnable onAddCategoryItemClick,
                                         BiConsumer<Transaction, List<Integer>> onSaveButtonClick,
                                         Consumer<Transaction> onDeleteButtonClick) {
        this.wallet = wallet;
        this.transaction = transactionWithCategories.getTransaction();
        this.categories = categories;
        transactionWithCategories.getCategories().forEach(category -> {
            this.selectedCategoryIds.add(category.getId());
        });
        this.onCategoryItemLongClick = onCategoryItemLongClick;
        this.onAddCategoryItemClick = onAddCategoryItemClick;
        this.onSaveButtonClick = onSaveButtonClick;
        this.onDeleteButtonClick = onDeleteButtonClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAddEditTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (onDeleteButtonClick != null) {
            setupEditBottomSheetView();
        } else {
            setupAddBottomSheetView();
        }
        setupTransactionAmountTextField();
        setupDatePicker();
        setupCategoriesRV();
        setupAddCategoryCard();
        setupDeleteButton();
        setupSaveButton();
    }

    private void setupAddBottomSheetView() {
        binding.btnDelete.setVisibility(View.GONE);
    }

    private void setupEditBottomSheetView() {
        binding.tvTitle.setText(R.string.edit_transaction);
        if (binding.tfTransactionTitle.getEditText() != null) {
            binding.tfTransactionTitle.getEditText().setText(transaction.getTitle());
        }
        if (binding.tfTransactionAmount.getEditText() != null) {
            binding.tfTransactionAmount.getEditText().setText(transaction.getAmount());
        }
        if (transaction.getIsIncome()) {
            binding.rbExpense.setChecked(false);
            binding.rbIncome.setChecked(true);
        }
        if (binding.tfTransactionDate.getEditText() != null) {
            binding.tfTransactionDate.getEditText().setText(transaction.getDate());
        }
    }

    private void setupTransactionAmountTextField() {
        binding.tfTransactionAmount.setPrefixText(wallet.getCurrency());
    }

    private void setupDatePicker() {
        binding.tfTransactionDate.setEndIconOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(R.string.select_date)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                LocalDateTime dateTime = Instant.ofEpochMilli(selection)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                transaction.setDate(dateTime.format(formatter));
                if (binding.tfTransactionDate.getEditText() != null) {
                    binding.tfTransactionDate.getEditText().setText(transaction.getDate());
                }
            });
            datePicker.show(getParentFragmentManager(), null);
        });
    }

    private void setupCategoriesRV() {
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        CategoriesAdapter adapter = getCategoriesAdapter();
        binding.rvCategories.setAdapter(adapter);
        categories.observe(getViewLifecycleOwner(), categories -> {
            List<Category> newCategories = new ArrayList<>(categories);
            newCategories.add(0, new Category(CategoriesViewModel.UNUSED_CATEGORY_ID, getString(R.string.all)));
            adapter.submitList(newCategories);
        });
        adapter.setSelectedCategoryIds(selectedCategoryIds);
    }

    @NonNull
    private CategoriesAdapter getCategoriesAdapter() {
        CategoriesAdapter adapter = new CategoriesAdapter();
        adapter.setOnItemClickCallback(new CategoriesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClick(Category category) {
                if (selectedCategoryIds.contains(category.getId())) {
                    selectedCategoryIds.remove(category.getId());
                } else {
                    selectedCategoryIds.add(category.getId());
                }
                adapter.setSelectedCategoryIds(selectedCategoryIds);
            }

            @Override
            public void onItemLongClick(Category category) {
                onCategoryItemLongClick.accept(category);
            }
        });
        return adapter;
    }

    private void setupAddCategoryCard() {
        binding.cardAddCategory.setOnClickListener(v -> onAddCategoryItemClick.run());
    }

    private void setupDeleteButton() {
        binding.btnDelete.setOnClickListener(v -> onDeleteButtonClick.accept(transaction));
    }

    private void setupSaveButton() {
        binding.btnSave.setOnClickListener(v -> {
            if (binding.tfTransactionTitle.getEditText() != null && transaction.getTitle() != null) {
                transaction.setDate(String.valueOf(binding.tfTransactionTitle.getEditText().getText()));
            }
            if (binding.tfTransactionAmount.getEditText() != null && binding.tfTransactionAmount.getEditText().getText().length() != 0) {
                transaction.setAmount(Integer.parseInt(String.valueOf(binding.tfTransactionAmount.getEditText().getText())));
            }
            binding.rgTransactionType.setOnCheckedChangeListener((group, checkedId) -> transaction.setIsIncome(checkedId == R.id.rb_income));
            if (binding.tfTransactionDate.getEditText() != null) {
                transaction.setDate(String.valueOf(binding.tfTransactionDate.getEditText().getText()));
            }
            onSaveButtonClick.accept(transaction, selectedCategoryIds);
            dismiss();
        });
    }
}
