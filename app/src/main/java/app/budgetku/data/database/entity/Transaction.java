package app.budgetku.data.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int walletId;
    private int amount;
    private String date;
    private String note;
    private boolean isCredit;

    public Transaction(int id, int walletId, int amount, String date, String note, boolean isCredit) {
        this.id = id;
        this.walletId = walletId;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.isCredit = isCredit;
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

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getIsCredit() {
        return this.isCredit;
    }

    public void setIsCredit(boolean isCredit) {
        this.isCredit = isCredit;
    }
}