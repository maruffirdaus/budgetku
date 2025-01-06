package app.budgetku.ui.shared.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.LocalDate;
import java.util.function.Consumer;

import app.budgetku.R;
import app.budgetku.databinding.DialogSelectYearBinding;

public class YearPickerDialog extends MaterialAlertDialogBuilder {
    DialogSelectYearBinding binding;

    public YearPickerDialog(@NonNull Context context, int currentSelectedYear,
                            Consumer<Integer> onOKButtonClick,
                            Consumer<Integer> onResetButtonClick) {
        super(context);
        binding = DialogSelectYearBinding.inflate(LayoutInflater.from(context));
        setupNumberPicker(currentSelectedYear);
        setupDialog(onOKButtonClick, onResetButtonClick);

    }

    private void setupNumberPicker(int currentSelectedYear) {
        NumberPicker yearPicker = binding.yearPicker;
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(2100);
        yearPicker.setValue(currentSelectedYear);
    }

    private void setupDialog(Consumer<Integer> onOKButtonClick,
                             Consumer<Integer> onResetButtonClick) {
        super
                .setView(binding.getRoot())
                .setPositiveButton(R.string.ok,
                        (dialog, which) -> onOKButtonClick.accept(binding.yearPicker.getValue()))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setNeutralButton(R.string.reset,
                        (dialog, which) -> onResetButtonClick.accept(LocalDate.now().getYear()));
    }
}