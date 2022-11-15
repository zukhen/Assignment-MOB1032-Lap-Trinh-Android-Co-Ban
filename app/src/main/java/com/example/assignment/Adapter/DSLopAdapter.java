package com.example.assignment.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.Model.Lop;
import com.example.assignment.R;

import java.util.ArrayList;

public class DSLopAdapter extends BaseAdapter {
    private ArrayList<Lop> listLop;
    private Context context;
    private LopDAO daoLop;
    public DSLopAdapter() {
    }

    public DSLopAdapter(ArrayList<Lop> listLop, Context context, LopDAO daoLop) {
        this.listLop = listLop;
        this.context = context;
        this.daoLop = daoLop;
    }

    @Override
    public int getCount() {
        return listLop.size();
    }

    @Override
    public Object getItem(int i) {
        return listLop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listLop.get(i).getID();
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        tao constructor
        ViewItemLop viewItemLop = new ViewItemLop();
        if (view == null) {
            view = inflater.inflate(R.layout.item_lop, null);
            viewItemLop.tv_tenLop = view.findViewById(R.id.tv_tenLop);
            viewItemLop.tv_maLop = view.findViewById(R.id.tv_maLop);
            viewItemLop.imgEdit = view.findViewById(R.id.imgEdit);
            viewItemLop.imgDel = view.findViewById(R.id.imgDel);
//          luu tru view
            view.setTag(viewItemLop);
        } else {
            viewItemLop = (ViewItemLop) view.getTag();
        }
//        set TextView
        viewItemLop.tv_maLop.setText(String.valueOf(listLop.get(i).getMaLop()));
        viewItemLop.tv_tenLop.setText(listLop.get(i).getTenLop());
//       bat su kien
        viewItemLop.imgEdit.setOnClickListener(view1 -> {
            editLopDiaLog(i,listLop.get(i).getID());

        });
        viewItemLop.imgDel.setOnClickListener(view1 -> {
            daoLop.deleteLop(listLop.get(i).getID());
            loadDanhSach();
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void editLopDiaLog(int vitri,int maLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_sua_lop, null);
        //        khai báo phần tử trong dialog
        AppCompatButton btn_editLop, btn_Cancel;
        EditText ed_suaMaLop, ed_suaTenLop;
        TextView tvError;
        //        ánh xạ
        btn_editLop = viewDialog.findViewById(R.id.btn_editLop);
        btn_Cancel = viewDialog.findViewById(R.id.btn_Cancel);
        ed_suaMaLop = viewDialog.findViewById(R.id.ed_suaMaLop);
        ed_suaTenLop = viewDialog.findViewById(R.id.ed_suaTenLop);
        tvError = viewDialog.findViewById(R.id.tvError);
        //      set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //      set EditText
        ed_suaMaLop.setText(listLop.get(vitri).getMaLop());
        ed_suaTenLop.setText(listLop.get(vitri).getTenLop());
        //      bắt sự kiện

        btn_editLop.setOnClickListener(view -> {
            if (ed_suaMaLop.getText().toString().isEmpty() || ed_suaTenLop.getText().toString().isEmpty()) {
                tvError.setText("Vui lòng không để trống");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvError.setText("");
                    }
                }, 2000);
            } else {
                Lop lop = new Lop();
                lop.setMaLop(ed_suaMaLop.getText().toString());
                lop.setTenLop(ed_suaTenLop.getText().toString());
                if (daoLop.updateLop(lop,maLoai)) {
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
    private void loadDanhSach(){
        listLop.clear();
        listLop = daoLop.getAllLop();
        notifyDataSetChanged();
    }
    private static class ViewItemLop {
        TextView tv_tenLop, tv_maLop;
        ImageView imgDel, imgEdit;
    }
}
