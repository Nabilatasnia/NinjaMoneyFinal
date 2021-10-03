package com.example.ninjamoney.BalanceCalculation;

public class BalanceData {
    int incomeTotal;
    int incomeCash;
    int incomeBank;
    int incomeMobile;
    int expenseTotal;
    int expenseCash;
    int expenseBank;
    int expenseMobile;

    public int getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(int incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public int getIncomeCash() {
        return incomeCash;
    }

    public void setIncomeCash(int incomeCash) {
        this.incomeCash = incomeCash;
    }

    public int getIncomeBank() {
        return incomeBank;
    }

    public void setIncomeBank(int incomeBank) {
        this.incomeBank = incomeBank;
    }

    public int getIncomeMobile() {
        return incomeMobile;
    }

    public void setIncomeMobile(int incomeMobile) {
        this.incomeMobile = incomeMobile;
    }

    public int getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(int expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    public int getExpenseCash() {
        return expenseCash;
    }

    public void setExpenseCash(int expenseCash) {
        this.expenseCash = expenseCash;
    }

    public int getExpenseBank() {
        return expenseBank;
    }

    public void setExpenseBank(int expenseBank) {
        this.expenseBank = expenseBank;
    }

    public int getExpenseMobile() {
        return expenseMobile;
    }

    public void setExpenseMobile(int expenseMobile) {
        this.expenseMobile = expenseMobile;
    }

    public BalanceData(int incomeTotal, int incomeCash, int incomeBank, int incomeMobile, int expenseTotal, int expenseCash, int expenseBank, int expenseMobile) {
        this.incomeTotal = incomeTotal;
        this.incomeCash = incomeCash;
        this.incomeBank = incomeBank;
        this.incomeMobile = incomeMobile;
        this.expenseTotal = expenseTotal;
        this.expenseCash = expenseCash;
        this.expenseBank = expenseBank;
        this.expenseMobile = expenseMobile;
    }
}
