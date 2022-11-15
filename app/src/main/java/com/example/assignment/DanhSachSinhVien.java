package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assignment.Adapter.DSinhVienAdapter;
import com.example.assignment.DAO.SinhVienDAO;

import com.example.assignment.Model.SinhVien;

import java.util.ArrayList;
import java.util.Locale;

public class DanhSachSinhVien extends AppCompatActivity{
    private ListView lv_sinhVien;
    private ArrayList<SinhVien> listSinhVien;
    private SinhVienDAO daoSinhVien;
    private Intent intent;
    private DSinhVienAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveLanguage();

        setContentView(R.layout.activity_danh_sach_sinh_vien);
        //        find id
        ImageView btn_backToMain = findViewById(R.id.btn_backToMain);
        lv_sinhVien = findViewById(R.id.lv_sinhVien);
        listSinhVien = new ArrayList<>();
        context = this;
        daoSinhVien = new SinhVienDAO(context);
//        bat su kien
        btn_backToMain.setOnClickListener(view -> {
            intent = new Intent(DanhSachSinhVien.this, MainActivity.class);
            startActivity(intent);
        });

        listSinhVien.clear();
        listSinhVien = daoSinhVien.getAllSinhVien();
        adapter = new DSinhVienAdapter(listSinhVien, context, daoSinhVien);
        lv_sinhVien.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // thay doi ngon ngu
     void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        //    luu ngon ngu SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Language", MODE_PRIVATE).edit();
        editor.putString("my_Lang", language);
        editor.apply();
    }

    //    lay ngon ngu trong SharedPreferences
    private void saveLanguage() {
        SharedPreferences prefs = getSharedPreferences("Language", MODE_PRIVATE);
        String language = prefs.getString("my_Lang", "");
        changeLanguage(language);
    }
}