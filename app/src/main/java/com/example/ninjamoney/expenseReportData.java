package com.example.ninjamoney;

public class expenseReportData {
    int janexp;
    int febexp;
    int marexp;
    int aprexp;
    int mayexp;
    int junexp;
    int julexp;
    int augexp;
    int sepexp;
    int octexp;
    int novexp;
    int decexp;

    public expenseReportData(int janexp, int febexp, int marexp, int aprexp, int mayexp, int junexp, int julexp, int augexp, int sepexp, int octexp, int novexp, int decexp) {
        this.janexp = janexp;
        this.febexp = febexp;
        this.marexp = marexp;
        this.aprexp = aprexp;
        this.mayexp = mayexp;
        this.junexp = junexp;
        this.julexp = julexp;
        this.augexp = augexp;
        this.sepexp = sepexp;
        this.octexp = octexp;
        this.novexp = novexp;
        this.decexp = decexp;
    }

    public int getJanexp() {
        return janexp;
    }

    public void setJanexp(int janexp) {
        this.janexp = janexp;
    }

    public int getFebexp() {
        return febexp;
    }

    public void setFebexp(int febexp) {
        this.febexp = febexp;
    }

    public int getMarexp() {
        return marexp;
    }

    public void setMarexp(int marexp) {
        this.marexp = marexp;
    }

    public int getAprexp() {
        return aprexp;
    }

    public void setAprexp(int aprexp) {
        this.aprexp = aprexp;
    }

    public int getMayexp() {
        return mayexp;
    }

    public void setMayexp(int mayexp) {
        this.mayexp = mayexp;
    }

    public int getJunexp() {
        return junexp;
    }

    public void setJunexp(int junexp) {
        this.junexp = junexp;
    }

    public int getJulexp() {
        return julexp;
    }

    public void setJulexp(int julexp) {
        this.julexp = julexp;
    }

    public int getAugexp() {
        return augexp;
    }

    public void setAugexp(int augexp) {
        this.augexp = augexp;
    }

    public int getSepexp() {
        return sepexp;
    }

    public void setSepexp(int sepexp) {
        this.sepexp = sepexp;
    }

    public int getOctexp() {
        return octexp;
    }

    public void setOctexp(int octexp) {
        this.octexp = octexp;
    }

    public int getNovexp() {
        return novexp;
    }

    public void setNovexp(int novexp) {
        this.novexp = novexp;
    }

    public int getDecexp() {
        return decexp;
    }

    public void setDecexp(int decexp) {
        this.decexp = decexp;
    }

    @Override
    public String toString() {
        return "expenseReportData{" +
                "janexp=" + janexp +
                ", febexp=" + febexp +
                ", marexp=" + marexp +
                ", aprexp=" + aprexp +
                ", mayexp=" + mayexp +
                ", junexp=" + junexp +
                ", julexp=" + julexp +
                ", augexp=" + augexp +
                ", sepexp=" + sepexp +
                ", octexp=" + octexp +
                ", novexp=" + novexp +
                ", decexp=" + decexp +
                '}';
    }
}


