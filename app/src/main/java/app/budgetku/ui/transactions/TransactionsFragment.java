package app.budgetku.ui.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.NumberFormat;
import java.util.Objects;

import app.budgetku.R;
import app.budgetku.databinding.FragmentTransactionsBinding;
import app.budgetku.ui.shared.listener.FragmentListener;
import app.budgetku.ui.shared.viewmodel.CategoriesViewModel;
import app.budgetku.ui.shared.viewmodel.TransactionsViewModel;
import app.budgetku.ui.shared.viewmodel.WalletsViewModel;
import app.budgetku.ui.shared.widget.YearPickerDialog;

public class TransactionsFragment extends Fragment {
    private FragmentTransactionsBinding binding;
    private WalletsViewModel walletsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private TransactionsViewModel transactionsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupTopAppBar();
        setupViewPager();
        setupSnackbar();
    }

    @Override
    public void onPause() {
        super.onPause();
        resetViewModelMessages();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupViewModel() {
        walletsViewModel = new ViewModelProvider(requireActivity()).get(WalletsViewModel.class);
        categoriesViewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
        transactionsViewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
        transactionsViewModel.setFilteredTransactionsEnabled(true);
    }

    private void setupTopAppBar() {
        binding.topAppBar.setNavigationOnClickListener(v -> ((FragmentListener) requireActivity()).onToolbarNavigationClick());
        walletsViewModel.selectedWallet.observe(getViewLifecycleOwner(), selectedWallet -> {
            binding.topAppBar.setTitle(selectedWallet.getName());
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String formattedBalance = numberFormat.format(selectedWallet.getBalance());
            binding.topAppBar
                    .setSubtitle(new StringBuilder().append(selectedWallet.getCurrency()).append(formattedBalance));
        });
        Button btnYearFilter = Objects
                .requireNonNull(binding.topAppBar.getMenu().getItem(0).getActionView())
                .findViewById(R.id.btn_year_filter);
        transactionsViewModel.selectedYear.observe(getViewLifecycleOwner(), selectedYear -> {
            btnYearFilter.setText(String.valueOf(selectedYear));
            transactionsViewModel.getTransactions();
        });
        btnYearFilter.setOnClickListener(v -> {
            if (transactionsViewModel.selectedYear.getValue() != null) {
                new YearPickerDialog(requireActivity(), transactionsViewModel.selectedYear.getValue(),
                        selectedYear -> transactionsViewModel.setSelectedYear(selectedYear),
                        actualYear -> transactionsViewModel.setSelectedYear(actualYear)).show();
            }
        });
    }

    private void setupViewPager() {
        TransactionsSectionAdapter adapter = new TransactionsSectionAdapter(requireActivity());
        String[] monthFilters = getResources().getStringArray(R.array.month_filters);
        binding.viewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(monthFilters[position])).attach();
        if (transactionsViewModel.selectedMonth.getValue() != null) {
            binding.viewPager.setCurrentItem(transactionsViewModel.selectedMonth.getValue(), false);
        }
    }

    private void setupSnackbar() {
        walletsViewModel.walletRelatedMessage.observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });
        categoriesViewModel.categoryRelatedMessage.observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });
        transactionsViewModel.transactionRelatedMessage.observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void resetViewModelMessages() {
        walletsViewModel.setWalletRelatedMessage(null);
        categoriesViewModel.setCategoryRelatedMessage(null);
        transactionsViewModel.setTransactionRelatedMessage(null);
    }
}