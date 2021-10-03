package com.example.ninjamoney.BalanceCalculation;

public class BalanceDataExpense {
    int totalTotal;
    int cashTotal;
    int bankTotal;
    int mobileTotal;

    public BalanceDataExpense() {
        this.totalTotal = 0;
        this.cashTotal = 0;
        this.bankTotal = 0;
        this.mobileTotal = 0;
    }

    public BalanceDataExpense(int cashTotal, int bankTotal, int mobileTotal, int totalTotal) {
        this.totalTotal = totalTotal;
        this.cashTotal = cashTotal;
        this.bankTotal = bankTotal;
        this.mobileTotal = mobileTotal;
    }

    public int getTotalTotal() {
        return totalTotal;
    }

    public void setTotalTotal(int totalTotal) {
        this.totalTotal = totalTotal;
    }

    public int getCashTotal() {
        return cashTotal;
    }

    public void setCashTotal(int cashTotal) {
        this.cashTotal = cashTotal;
    }

    public int getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(int bankTotal) {
        this.bankTotal = bankTotal;
    }

    public int getMobileTotal() {
        return mobileTotal;
    }

    public void setMobileTotal(int mobileTotal) {
        this.mobileTotal = mobileTotal;
    }
}
