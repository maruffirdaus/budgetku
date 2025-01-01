package app.budgetku.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public abstract class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Amount")
    private int amount;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Note")
    private String note;

    @ColumnInfo(name = "is_credit")
    private boolean isCredit;

    public Transaction(int id, int amount,String date, String note,boolean isCredit){
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.isCredit = isCredit;
    }

    public int getId(){
        return this.id;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getNote(){
        return this.note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public boolean getIsCredit(){
        return this.isCredit;
    }

    public void setIsCredit(boolean isCredit){
        this.isCredit = isCredit;
    }
}
