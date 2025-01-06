package app.budgetku.ui.shared.widget;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import app.budgetku.R;

public class DeleteTransactionDialog extends MaterialAlertDialogBuilder {

    public DeleteTransactionDialog(@NonNull Context context, Runnable onOKButtonClick) {
        super(context);
        setupDialog(onOKButtonClick);
    }

    private void setupDialog(Runnable onOKButtonClick) {
        super
                .setTitle(R.string.delete_transaction)
                .setMessage(R.string.delete_transaction_message)
                .setPositiveButton(R.string.ok, ((dialog, which) -> onOKButtonClick.run()))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()));
    }
}