package com.example.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.Database.DbHelper;
import com.example.assignment.Model.Lop;

import java.util.ArrayList;

public class LopDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public LopDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Lop> getAllLop() {
        ArrayList<Lop> list = new ArrayList<>();
        String sql = "SELECT * FROM Lop";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new Lop(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        return list;
    }

    public int getCount() {
        ArrayList<Lop> list = new ArrayList<>();
        String sql = "SELECT * FROM Lop";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    public boolean insertLop(Lop lp) {
        ContentValues values = new ContentValues();
        values.put("maLop", lp.getMaLop());
        values.put("tenLop", lp.getTenLop());
        long kq = db.insert("Lop", null, values);
        return kq != -1;
    }

    public void deleteLop(int id) {
        long kq = db.delete("Lop", "ID=?", new String[]{String.valueOf(id)});
    }

    public boolean updateLop(Lop lp, int maLoai) {
        ContentValues values = new ContentValues();
        values.put("maLop", lp.getMaLop());
        values.put("tenLop", lp.getTenLop());
        long kq = db.update("Lop", values, "ID=?", new String[]{String.valueOf(maLoai)});
        return kq != -1;
    }

}
