package com.example.ninjamoney;

public class Data {
    private int amount;

    private String title;

    private String note;

    private String date;
    private String account;

    public Data(int amount,String account, String date, String note, String title) {
        this.amount = amount;
        this.account=account;
        this.date = date;
        this.note=note;
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Data{" +
                "amount=" + amount +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +

                ", note='" + note + '\'' +

                ", date='" + date + '\'' +
                '}';
    }
}
