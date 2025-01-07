package app.budgetku.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;

import app.budgetku.R;
import app.budgetku.databinding.FragmentDashboardBinding;
import app.budgetku.ui.shared.listener.FragmentListener;
import app.budgetku.ui.shared.viewmodel.CategoriesViewModel;
import app.budgetku.ui.shared.viewmodel.TransactionsViewModel;
import app.budgetku.ui.shared.viewmodel.WalletsViewModel;
import app.budgetku.ui.shared.widget.AddEditWalletDialog;
import app.budgetku.ui.shared.widget.DeleteWalletDialog;
import app.budgetku.ui.shared.widget.YearPickerDialog;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private WalletsViewModel walletsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private TransactionsViewModel transactionsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupTopAppBar();
        setupWalletCard();
        setupYearFilterButton();
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
        transactionsViewModel.setFilteredTransactionsEnabled(false);
    }

    private void setupTopAppBar() {
        binding.topAppBar.setNavigationOnClickListener(v -> ((FragmentListener) requireActivity()).onToolbarNavigationClick());
    }

    private void setupWalletCard() {
        walletsViewModel.selectedWallet.observe(getViewLifecycleOwner(), selectedWallet -> {
            if (selectedWallet != null) {
                binding.tvWalletName.setText(selectedWallet.getName());
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                String formattedBalance = numberFormat.format(selectedWallet.getBalance());
                binding.tvWalletBalance
                        .setText(new StringBuilder().append(selectedWallet.getCurrency()).append(formattedBalance));
            } else {
                binding.tvWalletName.setText(R.string.no_wallet_selected);
                binding.tvWalletBalance.setText(R.string.empty_balance);
            }
        });
        binding.cardWallet.setOnClickListener(v -> {
            if (walletsViewModel.selectedWallet.getValue() != null) {
                new AddEditWalletDialog(requireActivity(),
                        walletsViewModel.selectedWallet.getValue(),
                        editedWallet -> walletsViewModel.editWallet(editedWallet),
                        walletToDelete -> new DeleteWalletDialog(requireActivity(),
                                () -> walletsViewModel.deleteWallet(walletToDelete)).show()).show();
            }
        });
    }

    private void setupYearFilterButton() {
        transactionsViewModel.selectedYear.observe(getViewLifecycleOwner(), selectedYear -> {
            transactionsViewModel.getTransactions();
            binding.btnYearFilter.setText(String.valueOf(selectedYear));
        });
        binding.btnYearFilter.setOnClickListener(v -> {
            if (transactionsViewModel.selectedYear.getValue() != null) {
                new YearPickerDialog(requireActivity(),
                        transactionsViewModel.selectedYear.getValue(),
                        selectedYear -> transactionsViewModel.setSelectedYear(selectedYear),
                        actualYear -> transactionsViewModel.setSelectedYear(actualYear)).show();
            }
        });
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