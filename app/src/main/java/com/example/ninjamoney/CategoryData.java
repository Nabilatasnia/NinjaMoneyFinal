package com.example.ninjamoney;

public class CategoryData {
    int food;
    int clothing;
    int living;
    int education;
    int treatment;
    int investment;
    int other;

    public CategoryData(int food, int clothing, int living, int education, int treatment, int investment, int other) {
        this.food = food;
        this.clothing = clothing;
        this.living = living;
        this.education = education;
        this.treatment = treatment;
        this.investment = investment;
        this.other = other;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getClothing() {
        return clothing;
    }

    public void setClothing(int clothing) {
        this.clothing = clothing;
    }

    public int getLiving() {
        return living;
    }

    public void setLiving(int living) {
        this.living = living;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getTreatment() {
        return treatment;
    }

    public void setTreatment(int treatment) {
        this.treatment = treatment;
    }

    public int getInvestment() {
        return investment;
    }

    public void setInvestment(int investment) {
        this.investment = investment;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "categoryData{" +
                "food=" + food +
                ", clothing=" + clothing +
                ", living=" + living +
                ", education=" + education +
                ", treatment=" + treatment +
                ", investment=" + investment +
                ", other=" + other +
                '}';
    }
}
