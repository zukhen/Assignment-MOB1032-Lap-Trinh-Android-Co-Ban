package com.example.assignment.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.Database.DbHelper;
import com.example.assignment.Model.Lop;
import com.example.assignment.Model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SinhVienDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public SinhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<SinhVien> getAllSinhVien() {
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(cursor.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(new SinhVien(cursor.getInt(0),cursor.getString(1), cursor.getString(2),date));
            cursor.moveToNext();
        }
        return list;
    }
    public boolean insertSinhVien(SinhVien sv)
    {
        ContentValues values = new ContentValues();
        values.put("maSV",sv.getMaSV());
        values.put("hoVaTen",sv.getHoVaTen());
        values.put("ngaySinh",simpleDateFormat.format(sv.getNgaySinh()));
        long kq= db.insert("SinhVien",null,values);
        if (kq==-1) {return false;}else{return true;}
    }

    public boolean deleteSinhVien(int id)
    {
        long kq= db.delete("SinhVien", "ID=?", new String[]{String.valueOf(id)});
        if (kq==-1) {return false;}else{return true;}
    }
    public boolean updateSinhVien(SinhVien sv,int maLoai)
    {
        ContentValues values = new ContentValues();
        values.put("maSV",sv.getMaSV());
        values.put("hoVaTen",sv.getHoVaTen());
        values.put("ngaySinh",simpleDateFormat.format(sv.getNgaySinh()));
        long kq= db.update("SinhVien",values,"ID=?",new String[]{String.valueOf(maLoai)});
        if (kq==-1) {return false;}else{return true;}
    }
    public String getNumberSinhVien() {
        String sql = "";
        Cursor cursor = db.rawQuery("SELECT COUNT(*)  FROM SinhVien", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            sql = cursor.getString(0);
        }
        return sql;
    }
}
