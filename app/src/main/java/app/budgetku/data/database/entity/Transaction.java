package app.budgetku.data.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Wallet.class,
                parentColumns = "id",
                childColumns = "walletId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int walletId;
    private String title;
    private int amount;
    private boolean isIncome;
    private String date;

    public Transaction(int id, int walletId, String title, int amount, boolean isIncome, String date) {
        this.id = id;
        this.walletId = walletId;
        this.title = title;
        this.amount = amount;
        this.isIncome = isIncome;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWalletId() {
        return this.walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getIsIncome() {
        return this.isIncome;
    }

    public void setIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}