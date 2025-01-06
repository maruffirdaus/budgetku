package app.budgetku.ui.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import app.budgetku.R;
import app.budgetku.data.database.entity.Category;
import app.budgetku.data.database.entity.Transaction;
import app.budgetku.data.database.entity.Wallet;
import app.budgetku.databinding.FragmentTransactionsSectionBinding;
import app.budgetku.ui.shared.adapter.CategoriesAdapter;
import app.budgetku.ui.shared.viewmodel.CategoriesViewModel;
import app.budgetku.ui.shared.viewmodel.TransactionsViewModel;
import app.budgetku.ui.shared.viewmodel.WalletsViewModel;
import app.budgetku.ui.shared.widget.AddEditCategoryDialog;
import app.budgetku.ui.shared.widget.DeleteCategoryDialog;
import app.budgetku.ui.shared.widget.DeleteTransactionDialog;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TransactionsSectionFragment extends Fragment {
    private static final String ARG_MONTH = "month";
    private FragmentTransactionsSectionBinding binding;
    private WalletsViewModel walletsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private TransactionsViewModel transactionsViewModel;
    private int month;

    public TransactionsSectionFragment() {
    }

    public static TransactionsSectionFragment newInstance(int month) {
        TransactionsSectionFragment fragment = new TransactionsSectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            month = getArguments().getInt(ARG_MONTH);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionsSectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupCategoriesRV();
        setupAddCategoryCard();
        setupTransactionsRV();
    }

    @Override
    public void onResume() {
        super.onResume();
        transactionsViewModel.setSelectedMonth(month);
        transactionsViewModel.getTransactions();
    }

    @Override
    public void onPause() {
        super.onPause();
        resetViewModelMessages();
    }

    private void setupViewModel() {
        walletsViewModel = new ViewModelProvider(requireActivity()).get(WalletsViewModel.class);
        categoriesViewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
        transactionsViewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
    }

    private void setupCategoriesRV() {
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        CategoriesAdapter adapter = getCategoriesAdapter();
        binding.rvCategories.setAdapter(adapter);
        categoriesViewModel.categories.observe(getViewLifecycleOwner(), categories -> {
            List<Category> newCategories = new ArrayList<>(categories);
            newCategories.add(0, new Category(CategoriesViewModel.UNUSED_CATEGORY_ID, getString(R.string.all)));
            adapter.submitList(newCategories);
        });
        categoriesViewModel.selectedCategoryId.observe(getViewLifecycleOwner(),
                selectedCategoryId -> {
                    adapter.setSelectedCategoryIds(List.of(selectedCategoryId));
                    transactionsViewModel.setSelectedCategoryId(selectedCategoryId);
                    transactionsViewModel.getTransactions();
                });
    }

    @NonNull
    private CategoriesAdapter getCategoriesAdapter() {
        return new CategoriesAdapter(new CategoriesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClick(Category category) {
                categoriesViewModel.setSelectedCategoryId(category.getId());
            }

            @Override
            public void onItemLongClick(Category category) {
                if (category.getId() != CategoriesViewModel.UNUSED_CATEGORY_ID) {
                    new AddEditCategoryDialog(requireActivity(), category,
                            categoriesViewModel::editCategory,
                            categoryToDelete -> new DeleteCategoryDialog(requireActivity(),
                                    () -> categoriesViewModel.deleteCategory(categoryToDelete)).show()).show();
                }
            }
        });
    }

    private void setupAddCategoryCard() {
        binding.cardAddCategory.setOnClickListener(v -> new AddEditCategoryDialog(requireActivity(),
                categoriesViewModel::addCategory).show());
    }

    private void setupTransactionsRV() {
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(requireActivity()));
        TransactionsAdapter adapter = getTransactionsAdapter();
        binding.rvTransactions.setAdapter(adapter);
        walletsViewModel.selectedWallet.observe(getViewLifecycleOwner(),
                selectedWallet -> adapter.setCurrency(selectedWallet.getCurrency()));
        transactionsViewModel.transactions.observe(getViewLifecycleOwner(), transactions -> {
            if (transactions.isEmpty()) {
                transactionsViewModel.setTransactionRelatedMessage(getString(R.string.no_transactions_data));
            } else {
                adapter.submitList(transactions);
            }
        });
    }

    @NonNull
    private TransactionsAdapter getTransactionsAdapter() {
        return new TransactionsAdapter("Default", new TransactionsAdapter.OnItemClickCallback() {
            @Override
            public void onItemClick(Transaction transaction) {

            }

            @Override
            public void onItemLongClick(Transaction transaction) {
                new DeleteTransactionDialog(requireActivity(),
                        () -> transactionsViewModel.deleteTransaction(transaction)).show();
            }
        });
    }

    private void resetViewModelMessages() {
        categoriesViewModel.setCategoryRelatedMessage(null);
        transactionsViewModel.setTransactionRelatedMessage(null);
    }
}