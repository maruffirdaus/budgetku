package app.budgetku.ui.shared.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.function.Consumer;

import app.budgetku.R;
import app.budgetku.data.database.entity.Wallet;
import app.budgetku.databinding.DialogAddEditWalletBinding;

public class AddEditWalletDialog extends MaterialAlertDialogBuilder {
    private final DialogAddEditWalletBinding binding;
    private final Wallet wallet;

    public AddEditWalletDialog(@NonNull Context context, Consumer<Wallet> onSaveButtonClick) {
        super(context);
        binding = DialogAddEditWalletBinding.inflate(LayoutInflater.from(context));
        this.wallet = new Wallet(0, "", "", 0);
        setupAddDialogView();
        setupAddDialog(onSaveButtonClick);
    }

    public AddEditWalletDialog(@NonNull Context context, Wallet wallet,
                                 Consumer<Wallet> onSaveButtonClick,
                                 Consumer<Wallet> onDeleteButtonClick) {
        super(context);
        binding = DialogAddEditWalletBinding.inflate(LayoutInflater.from(context));
        this.wallet = wallet;
        setupEditDialogView();
        setupEditDialog(onSaveButtonClick, onDeleteButtonClick);
    }

    private void setupAddDialogView() {
        if (binding.tfWalletCurrency.getEditText() != null) {
            binding.tfWalletCurrency.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    binding.tfWalletBalance.setPrefixText(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void setupEditDialogView() {
        binding.tvTitle.setText(R.string.edit_wallet);
        if (binding.tfWalletName.getEditText() != null) {
            binding.tfWalletName.getEditText().setText(wallet.getName());
        }
        binding.tfWalletCurrency.setVisibility(View.GONE);
        binding.tfWalletBalance.setVisibility(View.GONE);
    }

    private void setupAddDialog(Consumer<Wallet> onSaveButtonClick) {
        super
                .setView(binding.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    if (binding.tfWalletName.getEditText() != null) {
                        String walletName = String
                                .valueOf(binding.tfWalletName.getEditText().getText());
                        wallet.setName(walletName);
                    }
                    if (binding.tfWalletCurrency.getEditText() != null) {
                        String walletCurrency = String
                                .valueOf(binding.tfWalletCurrency.getEditText().getText());
                        wallet.setCurrency(walletCurrency);
                    }
                    if (binding.tfWalletBalance.getEditText() != null) {
                        int walletBalance;
                        try {
                            walletBalance = Integer
                                    .parseInt(String.valueOf(binding.tfWalletBalance.getEditText().getText()));
                        } catch (NumberFormatException e) {
                            walletBalance = 0;
                        }
                        wallet.setBalance(walletBalance);
                    }
                    onSaveButtonClick.accept(wallet);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
    }

    private void setupEditDialog(Consumer<Wallet> onSaveButtonClick,
                             Consumer<Wallet> onDeleteButtonClick) {
        super
                .setView(binding.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    if (binding.tfWalletName.getEditText() != null) {
                        String walletName = String
                                .valueOf(binding.tfWalletName.getEditText().getText());
                        wallet.setName(walletName);
                    }
                    onSaveButtonClick.accept(wallet);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setNeutralButton(R.string.delete,
                        (dialog, which) -> onDeleteButtonClick.accept(wallet));
    }
}