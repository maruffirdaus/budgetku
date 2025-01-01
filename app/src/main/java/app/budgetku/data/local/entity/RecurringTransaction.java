package app.budgetku.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class RecurringTransaction extends Transaction{
    @ColumnInfo(name = "Periode")
    private int periode;

    @ColumnInfo(name = "next_billing")
    private String nextBilling;

    public RecurringTransaction(int id,int amount,String date,String note,boolean isCredit){
        super(id,amount,date,note,isCredit);
    }

    public int getPeriode(){
        return this.periode;
    }

    public void setPeriode(int periode){
        this.periode = periode;
    }

    public String getNextBilling(){
        return this.nextBilling;
    }

    public void calculateNextBilling(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentBillingDate = LocalDate.parse(this.nextBilling, formatter);
        LocalDate newBillingDate = currentBillingDate.plusDays(this.periode);
        this.nextBilling = newBillingDate.format(formatter);
    }
}
