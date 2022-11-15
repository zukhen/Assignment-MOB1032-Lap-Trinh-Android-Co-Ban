package com.example.assignment.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private final String db_Lop="CREATE TABLE Lop(ID INTEGER PRIMARY KEY AUTOINCREMENT,maLop TEXT, tenLop TEXT);";
    private final String db_SinhVien="CREATE TABLE SinhVien(ID INTEGER PRIMARY KEY AUTOINCREMENT,maSV TEXT, hoVaTen TEXT,ngaySinh Date);";
    public DbHelper( Context context) {
        super(context, "qlsv", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_Lop);
        db.execSQL(db_SinhVien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Lop ");
        db.execSQL("DROP TABLE IF EXISTS SinhVien");
    }
}
