package app.budgetku.ui.transactions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TransactionsSectionAdapter extends FragmentStateAdapter {

    public TransactionsSectionAdapter(FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TransactionsSectionFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 13;
    }
}