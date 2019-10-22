package com.app.app.DATA.Entitys;

public class Pollos {
    private int ID;
    private int pollos;
    private String date;

    public Pollos(int ID, int pollos, String date) {
        this.ID = ID;
        this.pollos = pollos;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPollos() {
        return pollos;
    }

    public void setPollos(int pollos) {
        this.pollos = pollos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
