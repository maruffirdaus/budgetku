package app.budgetku.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import app.budgetku.data.database.entity.Wallet;

@Dao
public interface WalletDao {

    @Insert
    void insert(Wallet wallet);

    @Query("SELECT * FROM wallet")
    List<Wallet> getWallets();

    @Query("SELECT * FROM wallet WHERE id = :id")
    Wallet getWallet(int id);

    @Update
    void update(Wallet wallet);

    @Delete
    void delete(Wallet wallet);
}