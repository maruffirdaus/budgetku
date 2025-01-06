package app.budgetku.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import app.budgetku.R;
import app.budgetku.data.database.entity.Wallet;
import app.budgetku.databinding.ActivityMainBinding;
import app.budgetku.ui.dashboard.DashboardFragment;
import app.budgetku.ui.shared.listener.FragmentListener;
import app.budgetku.ui.shared.viewmodel.CategoriesViewModel;
import app.budgetku.ui.shared.viewmodel.TransactionsViewModel;
import app.budgetku.ui.shared.viewmodel.WalletsViewModel;
import app.budgetku.ui.shared.widget.AddEditCategoryDialog;
import app.budgetku.ui.shared.widget.AddEditTransactionBottomSheet;
import app.budgetku.ui.shared.widget.AddEditWalletDialog;
import app.budgetku.ui.shared.widget.DeleteCategoryDialog;
import app.budgetku.ui.transactions.TransactionsFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements FragmentListener {
    private static final String ARG_SELECTED_NAV_ITEM = "selected_nav_item";
    private static final int NAV_ITEM_DASHBOARD = 0;
    private static final int NAV_ITEM_TRANSACTIONS = 1;
    private ActivityMainBinding binding;
    private WalletsViewModel walletsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private TransactionsViewModel transactionsViewModel;
    private int selectedNavItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViewModel();
        initSharedPrefs();
        setupNavigationDrawer();
        if (savedInstanceState != null) {
            selectedNavItem = savedInstanceState.getInt(ARG_SELECTED_NAV_ITEM);
        } else {
            selectedNavItem = NAV_ITEM_DASHBOARD;
        }
        setupPage();
        setupBottomNavigation();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_SELECTED_NAV_ITEM, selectedNavItem);
    }

    @Override
    public void onToolbarNavigationClick() {
        binding.drawerLayout.open();
    }

    private void setupViewModel() {
        walletsViewModel = new ViewModelProvider(this).get(WalletsViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
    }

    private void initSharedPrefs() {
        SharedPreferences sharedPrefs = getSharedPreferences("budgetku_prefs", MODE_PRIVATE);
        boolean isSetupScreenLaunched = sharedPrefs.getBoolean("is_setup_screen_launched", false);
        if (!isSetupScreenLaunched) {
            //finish();
        } else {
            int selectedWalletId = sharedPrefs.getInt("selected_wallet_id", 0);
        }
    }

    private void setupNavigationDrawer() {
        HashMap<Integer, Wallet> itemIdWalletMap = new HashMap<>();
        Menu walletsSubMenu = binding.navigationDrawer.getMenu().addSubMenu(R.string.wallets);
        walletsViewModel.wallets.observe(this, wallets -> {
            itemIdWalletMap.clear();
            walletsSubMenu.clear();
            AtomicInteger itemId = new AtomicInteger(1000);
            wallets.forEach(wallet -> {
                itemId.getAndIncrement();
                itemIdWalletMap.put(itemId.get(), wallet);
                walletsSubMenu
                        .add(Menu.NONE, itemId.get(), Menu.NONE, wallet.getName())
                        .setIcon(R.drawable.ic_account_balance_wallet)
                        .setCheckable(true);
            });
            walletsSubMenu
                    .add(Menu.NONE, R.id.menu_add_wallet, Menu.NONE, R.string.add_wallet)
                    .setIcon(R.drawable.ic_add);
        });
        walletsViewModel.selectedWallet.observe(this, selectedWallet -> {
            transactionsViewModel.setSelectedWalletId(selectedWallet.getId());
            binding.navigationDrawer.setCheckedItem(1000 + selectedWallet.getId());
        });
        binding.navigationDrawer.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_add_wallet) {
                new AddEditWalletDialog(this,
                        wallet -> walletsViewModel.addWallet(wallet)).show();
                return false;
            } else {
                walletsViewModel.setSelectedWallet(itemIdWalletMap.get(item.getItemId()));
                binding.drawerLayout.close();
                return true;
            }
        });
    }

    private void setupPage() {
        if (selectedNavItem == NAV_ITEM_DASHBOARD) {
            replaceFragment(new DashboardFragment());
            binding.bottomNavigation.setSelectedItemId(R.id.menu_dashboard);
        } else if (selectedNavItem == NAV_ITEM_TRANSACTIONS) {
            replaceFragment(new TransactionsFragment());
            binding.bottomNavigation.setSelectedItemId(R.id.menu_transactions);
        }
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_dashboard) {
                replaceFragment(new DashboardFragment());
                selectedNavItem = NAV_ITEM_DASHBOARD;
                return true;
            } else if (item.getItemId() == R.id.menu_add_item) {
                if (walletsViewModel.selectedWallet.getValue() != null) {
                    new AddEditTransactionBottomSheet(walletsViewModel.selectedWallet.getValue(),
                            categoriesViewModel.categories,
                            category -> new AddEditCategoryDialog(this, category,
                                    categoriesViewModel::editCategory,
                                    categoryToDelete -> new DeleteCategoryDialog(this,
                                            () -> categoriesViewModel.deleteCategory(categoryToDelete)).show()).show(), () -> new AddEditCategoryDialog(this, categoriesViewModel::addCategory).show(),
                            transactionsViewModel::addTransaction).show(getSupportFragmentManager(),
                            null);
                }
                return false;
            } else if (item.getItemId() == R.id.menu_transactions) {
                replaceFragment(new TransactionsFragment());
                selectedNavItem = NAV_ITEM_TRANSACTIONS;
                return true;
            } else {
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        List<Fragment> oldFragments = getSupportFragmentManager().getFragments();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        oldFragments.forEach(oldFragment -> getSupportFragmentManager().beginTransaction().remove(oldFragment).commit());
    }
}