package app.budgetku.data.repository;

import java.util.List;

import app.budgetku.data.database.dao.WalletDao;
import app.budgetku.data.database.entity.Wallet;

public class WalletsRepository {
    private final WalletDao dao;

    public WalletsRepository(WalletDao dao) {
        this.dao = dao;
    }

    public List<Wallet> getWallets() {
        return dao.getWallets();
    }

    public Wallet getWallet(int id) {
        return dao.getWallet(id);
    }

    public void addWallet(Wallet wallet) {
        dao.insert(wallet);
    }

    public void editWallet(Wallet wallet) {
        dao.update(wallet);
    }

    public void deleteWallet(Wallet wallet) {
        dao.delete(wallet);
    }
}