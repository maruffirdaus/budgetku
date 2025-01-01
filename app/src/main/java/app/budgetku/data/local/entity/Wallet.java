package app.budgetku.data.local.entity;

public class Wallet extends Group{
    private int balance;

    public Wallet (int id, String name, int balance){
        super(id, name);
        this.balance = balance;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }

    public int getBalance(){
        return balance;
    }

}
