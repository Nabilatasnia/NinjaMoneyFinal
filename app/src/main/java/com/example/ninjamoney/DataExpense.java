package com.example.ninjamoney;

public class DataExpense {
    int amount;
    String category;
    String from;
    String date;
    String title;
    String note;

    public DataExpense(int amount, String category, String from, String date, String title,String note) {
        this.amount = amount;
        this.category = category;
        this.from = from;
        this.date = date;
        this.title = title;
        this.note=note;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "DataExpense{" +
                "amount=" + amount +
                ", category='" + category + '\'' +
                ", from='" + from + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
