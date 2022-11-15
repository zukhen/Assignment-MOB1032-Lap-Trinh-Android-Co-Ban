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

import com.example.assignment.Adapter.DSLopAdapter;
import com.example.assignment.DAO.LopDAO;
import com.example.assignment.Model.Lop;

import java.util.ArrayList;
import java.util.Locale;

public class DanhSachLop extends AppCompatActivity {
    private ImageView btn_backToMain;
    private ListView lv_Lop;
    private ArrayList<Lop> listLop;
    private LopDAO daoLop;
    private Intent intent;
    private DSLopAdapter adapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveLanguage();
        setContentView(R.layout.activity_danh_sach_lop);
        //        find id
        btn_backToMain = findViewById(R.id.btn_backToMain);
        lv_Lop = findViewById(R.id.lv_Lop);
        listLop = new ArrayList<>();
        context = this;
        daoLop = new LopDAO(getApplicationContext());
//        bat su kien
        btn_backToMain.setOnClickListener(view -> {
            intent = new Intent(DanhSachLop.this, MainActivity.class);
            startActivity(intent);
        });

        listLop.clear();
        listLop = daoLop.getAllLop();
        adapter = new DSLopAdapter(listLop,context,daoLop);
        lv_Lop.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void changeLanguage(String language) {
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