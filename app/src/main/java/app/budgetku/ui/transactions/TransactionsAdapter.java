package app.budgetku.ui.transactions;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import app.budgetku.R;
import app.budgetku.data.database.entity.Transaction;
import app.budgetku.databinding.RvTransactionsBinding;
import app.budgetku.ui.util.ThemeUtil;

public class TransactionsAdapter extends ListAdapter<Transaction, TransactionsAdapter.TransactionViewHolder> {
    private String currency;
    private final OnItemClickCallback onItemClickCallback;

    public TransactionsAdapter(String currency, OnItemClickCallback onItemClickCallback) {
        super(new TransactionDiffCallback());
        this.currency = currency;
        this.onItemClickCallback = onItemClickCallback;
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final RvTransactionsBinding binding;
        private final OnItemClickCallback onItemClickCallback;

        public TransactionViewHolder(RvTransactionsBinding binding,
                                     OnItemClickCallback onItemClickCallback) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickCallback = onItemClickCallback;
        }

        public void bind(String currency, Transaction transaction) {
            binding.tvTransactionTitle.setText(transaction.getTitle());
            if (transaction.getIsIncome()) {
                int incomeColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(), R.attr.colorIncome);
                int onIncomeColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(), R.attr.colorOnIncome);
                binding.imgDecoration.setColorFilter(onIncomeColor);
                binding.imgDecoration.setBackgroundColor(incomeColor);
                binding.tvTransactionAmount
                        .setText(new StringBuilder().append("+").append(currency).append(transaction.getAmount()));
                binding.tvTransactionAmount.setTextColor(incomeColor);
            } else {
                int expenseColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(), R.attr.colorExpense);
                int onExpenseColor = ThemeUtil.getThemeAttributeColor(itemView.getContext(), R.attr.colorOnExpense);
                binding.imgDecoration.setColorFilter(onExpenseColor);
                binding.imgDecoration.setBackgroundColor(expenseColor);
                binding.tvTransactionAmount
                        .setText(new StringBuilder().append("-").append(currency).append(transaction.getAmount()));
                binding.tvTransactionAmount.setTextColor(expenseColor);
            }
            binding.itemTransaction.setOnClickListener(v -> onItemClickCallback.onItemClick(transaction));
            binding.itemTransaction.setOnLongClickListener(v -> {
                onItemClickCallback.onItemLongClick(transaction);
                return true;
            });
        }
    }

    static class TransactionDiffCallback extends DiffUtil.ItemCallback<Transaction> {

        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }

    public interface OnItemClickCallback {
        void onItemClick(Transaction transaction);
        void onItemLongClick(Transaction transaction);
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvTransactionsBinding binding = RvTransactionsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionViewHolder(binding, onItemClickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = getItem(position);
        holder.bind(currency, transaction);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrency(String currency) {
        this.currency = currency;
        notifyDataSetChanged();
    }
}