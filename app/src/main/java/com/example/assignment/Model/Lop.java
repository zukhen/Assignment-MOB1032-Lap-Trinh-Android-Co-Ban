package com.example.assignment.Model;

public class Lop {
    private int ID;
    private String maLop;
    private String tenLop;
    public Lop()
    {

    }

    public Lop(int ID,String maLop, String tenLop) {
        this.ID = ID;
        this.maLop = maLop;
        this.tenLop = tenLop;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }
}
