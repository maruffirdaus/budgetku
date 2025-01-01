package app.budgetku.data.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int balance;

    public Wallet(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}