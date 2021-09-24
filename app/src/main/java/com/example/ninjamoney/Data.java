package com.example.ninjamoney;

public class Data {
    private int amount;
    private String category;
    private String title;
    private String source;
    private String note;
    private String id;
    private String date;

    public Data()
    {

    }

    public Data(int amount, String title,  String date, String note) {
        this.amount = amount;

        this.title = title;
        //this.source=source;

        this.date = date;
        this.note=note;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
