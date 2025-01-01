package app.budgetku.data.database.entity;

import androidx.room.Entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class RecurringTransaction extends Transaction {
    private int period;
    private String nextBilling;

    public RecurringTransaction(int id, int walletId, int amount, String date, String note, boolean isCredit, int period) {
        super(id, walletId, amount, date, note, isCredit);
        this.period = period;
        calculateNextBilling();
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getNextBilling() {
        return this.nextBilling;
    }

    public void setNextBilling(String nextBilling) {
        this.nextBilling = nextBilling;
    }

    public void calculateNextBilling() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentBillingDate = LocalDate.parse(this.nextBilling, formatter);
        LocalDate newBillingDate = currentBillingDate.plusDays(this.period);
        this.nextBilling = newBillingDate.format(formatter);
    }
}