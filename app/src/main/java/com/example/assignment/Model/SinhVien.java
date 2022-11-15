package com.example.assignment.Model;

import java.util.Date;

public class SinhVien {
    private int ID;
    private String maSV;
    private String hoVaTen;
    private Date ngaySinh;
    public SinhVien(){

    }

    public SinhVien(int ID, String maSV, String hoVaTen, Date ngaySinh) {
        this.ID = ID;
        this.maSV = maSV;
        this.hoVaTen = hoVaTen;
        this.ngaySinh = ngaySinh;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
