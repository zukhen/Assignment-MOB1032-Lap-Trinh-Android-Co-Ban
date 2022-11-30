package com.example.assignment;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.Model.Lop;
import com.example.assignment.Model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //    sử dụng LinearLayout thay cho Button
    private Context context;
    private LinearLayout btn_addLop, btn_addSinhVien, btn_xemDSLop, btn_xemDSSinhVien;
    private LopDAO daoLop;
    private SinhVienDAO daoSinhVien;
    private ImageView btn_Setting;

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        check language tu SharedPreferences
        saveLanguage();
        setContentView(R.layout.activity_main);
//        Ánh xạ
        context = this;
        btn_addLop = findViewById(R.id.btn_addLop);
        btn_addSinhVien = findViewById(R.id.btn_addSinhVien);
        btn_xemDSLop = findViewById(R.id.btn_xemDSLop);
        btn_xemDSSinhVien = findViewById(R.id.btn_xemDSSinhVien);
        daoLop = new LopDAO(context);
        btn_Setting = findViewById(R.id.btn_Setting);
        daoSinhVien = new SinhVienDAO(context);
//        bắt sự kiện
        btn_addLop.setOnClickListener(view -> {
            addLop();
        });
        btn_addSinhVien.setOnClickListener(view -> {
            addSinhVien();
        });
        btn_xemDSLop.setOnClickListener(view -> {
            intent = new Intent(context, DanhSachLop.class);
            startActivity(intent);
            finish();
        });
        btn_xemDSSinhVien.setOnClickListener(view -> {
            intent = new Intent(context, DanhSachSinhVien.class);
            startActivity(intent);
            finish();
        });
        btn_Setting.setOnClickListener(view -> {
            showChangeLanguageDialog();
        });


    }


    //    sử dụng hàm dialog thêm Lớp;
    private void addLop() {
//        khai bao dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_them_lop, null);
//        khai báo phần tử trong dialog
        AppCompatButton btn_createLop, btn_cancelLop;
        EditText ed_maLop, ed_tenLop;
        TextView tvError;
//        ánh xạ
        btn_createLop = viewDialog.findViewById(R.id.btn_createLop);
        btn_cancelLop = viewDialog.findViewById(R.id.btn_cancelLop);
        ed_maLop = viewDialog.findViewById(R.id.ed_maLop);
        ed_tenLop = viewDialog.findViewById(R.id.ed_tenLop);
        tvError = viewDialog.findViewById(R.id.tvError);
//      set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvError.setText("");
//        bắt sự kiện
        btn_createLop.setOnClickListener(view -> {
            if (ed_maLop.getText().toString().isEmpty() || ed_tenLop.getText().toString().isEmpty()) {
                tvError.setText(R.string.errror);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvError.setText("");
                    }
                }, 2000);
            } else {
                Lop lop = new Lop();
                lop.setMaLop(ed_maLop.getText().toString().toUpperCase());
                lop.setTenLop(ed_tenLop.getText().toString());
                if (daoLop.insertLop(lop)) {
                    Toast.makeText(context, R.string.addSuccess, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, R.string.addFailed, Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_cancelLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        show
        dialog.show();
    }


    //    add Sinh Viên
    @SuppressLint("SetTextI18n")
    private void addSinhVien() {
//        khai bao dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_them_sinh_vien, null);
//        khai báo phần tử trong dialog
        AppCompatButton btn_createSV, btn_cancelSV;
        EditText ed_maSV, ed_hoVaTen, ed_ngaySinh;
        ImageView date_picker_actions;
        TextView tvError;
//        ánh xạ
        date_picker_actions = viewDialog.findViewById(R.id.date_picker_actions);
        btn_cancelSV = viewDialog.findViewById(R.id.btn_cancelSV);
        ed_maSV = viewDialog.findViewById(R.id.ed_maSV);
        ed_hoVaTen = viewDialog.findViewById(R.id.ed_hoVaTen);
        ed_ngaySinh = viewDialog.findViewById(R.id.ed_ngaySinh);
        btn_createSV = viewDialog.findViewById(R.id.btn_createSV);
        tvError = viewDialog.findViewById(R.id.tvError);
//      set view
        tvError.setText("");

        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        bắt sự kiện
        date_picker_actions.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(i, i1, i2);
                    ed_ngaySinh.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }, 1980, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        btn_createSV.setOnClickListener(view -> {
            if (ed_maSV.getText().toString().isEmpty() || ed_hoVaTen.getText().toString().isEmpty() || ed_ngaySinh.getText().toString().isEmpty()) {
                tvError.setText(R.string.errror);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvError.setText("");
                    }
                }, 2000);
            } else {
                SinhVien sv = new SinhVien();
                sv.setMaSV(ed_maSV.getText().toString().toUpperCase());
                sv.setHoVaTen(ed_hoVaTen.getText().toString());
                try {
                    sv.setNgaySinh(simpleDateFormat.parse(ed_ngaySinh.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (daoSinhVien.insertSinhVien(sv)) {
                    Toast.makeText(context, R.string.addSuccess, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, R.string.addFailed, Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_cancelSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        show
        dialog.show();
    }

    // thay doi ngon ngu
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

    private void showChangeLanguageDialog() {
//        mang chua ngon ngu hien thi
//        tiếng việt, tiếng anh, tiếng hindi, tiêng Pháp
        final String[] listItems = {"Tiếng Việt", "English", "हिन्दी", "Français"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.language);
        builder.setSingleChoiceItems(listItems, -1, (dialogInterface, i) -> {
            switch (i) {
                case 0:
                    changeLanguage("vi");
                    recreate();
                    break;
                case 1:
                    changeLanguage("en");
                    recreate();
                    break;
                case 2:
                    changeLanguage("hi");
                    recreate();
                    break;
                case 3:
                    changeLanguage("fr");
                    recreate();
                    break;
            }
            dialogInterface.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}