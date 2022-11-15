package com.example.assignment.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.Model.Lop;
import com.example.assignment.Model.SinhVien;
import com.example.assignment.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DSinhVienAdapter extends BaseAdapter {
    private ArrayList<SinhVien> listSinhVien;
    private Context context;
    private SinhVienDAO daoSinhVien;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public DSinhVienAdapter() {
    }

    public DSinhVienAdapter(ArrayList<SinhVien> listSinhVien, Context context, SinhVienDAO daoSinhVien) {
        this.listSinhVien = listSinhVien;
        this.context = context;
        this.daoSinhVien = daoSinhVien;
    }

    @Override
    public int getCount() {
        return listSinhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return listSinhVien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listSinhVien.get(i).getID();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        tao constructor
        ViewItemSinhVien viewItemSinhVien = new ViewItemSinhVien();
        if (view == null) {
            view = inflater.inflate(R.layout.item_sinh_vien, null);
            viewItemSinhVien.tv_tenSinhVien = view.findViewById(R.id.tv_tenSinhVien);
            viewItemSinhVien.tv_ngaySinh = view.findViewById(R.id.tv_ngaySinh);
            viewItemSinhVien.imgEdit = view.findViewById(R.id.imgEdit);
            viewItemSinhVien.imgDel = view.findViewById(R.id.imgDel);
//          luu tru view
            view.setTag(viewItemSinhVien);
        } else {
            viewItemSinhVien = (ViewItemSinhVien) view.getTag();
        }
//        set TextView
        viewItemSinhVien.tv_tenSinhVien.setText(String.valueOf(listSinhVien.get(i).getHoVaTen()));
        viewItemSinhVien.tv_ngaySinh.setText(simpleDateFormat.format(listSinhVien.get(i).getNgaySinh()));
//       bat su kien
        viewItemSinhVien.imgEdit.setOnClickListener(view1 -> {
            editLopDiaLog(i,listSinhVien.get(i).getID());

        });
        viewItemSinhVien.imgDel.setOnClickListener(view1 -> {
            daoSinhVien.deleteSinhVien(listSinhVien.get(i).getID());
            loadDanhSach();
        });
        viewItemSinhVien.tv_tenSinhVien.setOnClickListener(view1 -> {
            showDialog(i);
        });
        viewItemSinhVien.tv_ngaySinh.setOnClickListener(view1 -> {
            showDialog(i);
        });

        return view;
    }
    @SuppressLint("SetTextI18n")
    private void showDialog(int vitri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_thong_tin_sinh_vien, null);
        TextView tv_maSinhVien, tv_hoVaTen, tv_ngaySinh;
//        findID
        tv_maSinhVien = viewDialog.findViewById(R.id.tv_maSinhVien);
        tv_hoVaTen = viewDialog.findViewById(R.id.tv_hoVaTen);
        tv_ngaySinh = viewDialog.findViewById(R.id.tv_ngaySinh);
//        setThongTin
        tv_maSinhVien.setText("Mã SV: " + listSinhVien.get(vitri).getMaSV());
        tv_hoVaTen.setText("Họ và tên: " + listSinhVien.get(vitri).getHoVaTen());
        tv_ngaySinh.setText("Ngày sinh: " + simpleDateFormat.format(listSinhVien.get(vitri).getNgaySinh()));
        //        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    @SuppressLint("SetTextI18n")
    private void editLopDiaLog(int vitri,int maLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_sua_sinh_vien, null);
        //        khai báo phần tử trong dialog
        AppCompatButton btn_editSV, btn_Cancel;
        EditText ed_suaMaSV, ed_suaHoVaTen, ed_suaNgaySinh;
        ImageView date_picker_actions;
        TextView tvError;
        //        ánh xạ
        ed_suaMaSV = viewDialog.findViewById(R.id.ed_suaMaSV);
        ed_suaHoVaTen = viewDialog.findViewById(R.id.ed_suaHoVaTen);
        ed_suaNgaySinh = viewDialog.findViewById(R.id.ed_ngaySinh);
        date_picker_actions = viewDialog.findViewById(R.id.date_picker_actions);
        btn_editSV = viewDialog.findViewById(R.id.btn_editSV);
        btn_Cancel = viewDialog.findViewById(R.id.btn_Cancel);
        tvError = viewDialog.findViewById(R.id.tvError);
        //      set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //      set EditText
        ed_suaMaSV.setText(listSinhVien.get(vitri).getMaSV());
        ed_suaHoVaTen.setText(listSinhVien.get(vitri).getHoVaTen());
        ed_suaNgaySinh.setText(simpleDateFormat.format(listSinhVien.get(vitri).getNgaySinh()));
        //      bắt sự kiện
        date_picker_actions.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(i, i1, i2);
                    ed_suaNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }, 1980, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        btn_editSV.setOnClickListener(view -> {
            if (ed_suaMaSV.getText().toString().isEmpty() || ed_suaHoVaTen.getText().toString().isEmpty() || ed_suaNgaySinh.getText().toString().isEmpty()) {
                tvError.setText("Vui lòng không để trống");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvError.setText("");
                    }
                }, 2000);
            } else {
                SinhVien sv = new SinhVien();
                sv.setMaSV(ed_suaMaSV.getText().toString());
                sv.setHoVaTen(ed_suaHoVaTen.getText().toString());
                try {
                    sv.setNgaySinh(simpleDateFormat.parse(ed_suaNgaySinh.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (daoSinhVien.updateSinhVien(sv,maLoai)) {
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_LONG).show();
                }
                loadDanhSach();
            }
        });
        btn_Cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();

    }

    private void loadDanhSach() {
        listSinhVien.clear();
        listSinhVien = daoSinhVien.getAllSinhVien();
        notifyDataSetChanged();
    }

    private static class ViewItemSinhVien {
        TextView tv_tenSinhVien, tv_ngaySinh;
        ImageView imgDel, imgEdit;
    }
}
