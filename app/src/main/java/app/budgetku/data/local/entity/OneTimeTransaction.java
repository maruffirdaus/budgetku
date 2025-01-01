package app.budgetku.data.local.entity;

import androidx.room.Entity;

@Entity
public class OneTimeTransaction extends Transaction{
    public OneTimeTransaction(int id,int amount,String date,String note,boolean isCredit){
        super(id, amount, date, note, isCredit);
    }
}
