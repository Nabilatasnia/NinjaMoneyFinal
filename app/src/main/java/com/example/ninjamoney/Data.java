package com.example.ninjamoney;

public class Data {
    private int amount;
    private String category;
    private String title;
    private String source;
    private String note;
    private String id;
    private String date;

    public Data(int amount, String date, String note, String title) {
        this.amount = amount;
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
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", note='" + note + '\'' +
                ", id='" + id + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
