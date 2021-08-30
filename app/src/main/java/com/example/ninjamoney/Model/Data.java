package com.example.ninjamoney.Model;

public class Data {
    private int amount;
    private String category;
    private String title;
    private String source;
    private String id;
    private String date;
    private Data()
    {

    }

    public Data(int amount, String category, String title, String source, String id, String date) {
        this.amount = amount;
        this.category = category;
        this.title = title;
        this.source = source;
        this.id = id;
        this.date = date;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
}