package app.budgetku.ui.shared.widget;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import app.budgetku.R;

public class DeleteWalletDialog extends MaterialAlertDialogBuilder {

    public DeleteWalletDialog(@NonNull Context context, Runnable onOKButtonClick) {
        super(context);
        setupDialog(onOKButtonClick);
    }

    private void setupDialog(Runnable onOKButtonClick) {
        super
                .setTitle(R.string.delete_wallet)
                .setMessage(R.string.delete_wallet_message)
                .setPositiveButton(R.string.ok, ((dialog, which) -> onOKButtonClick.run()))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()));
    }
}