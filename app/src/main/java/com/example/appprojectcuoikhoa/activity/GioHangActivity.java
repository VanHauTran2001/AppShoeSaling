package com.example.appprojectcuoikhoa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.ActivityGioHangBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.appprojectcuoikhoa.Utils.Utils;
import com.example.appprojectcuoikhoa.adapter.GioHang_Adapter;
import com.example.appprojectcuoikhoa.data.FavoriteDB;
import com.example.appprojectcuoikhoa.fragment.ThongBaoFragment;
import com.example.appprojectcuoikhoa.model.GioHang;
import com.example.appprojectcuoikhoa.model.ThongBao;

public class GioHangActivity extends AppCompatActivity {
    static ActivityGioHangBinding binding;
    static GioHang_Adapter gioHangAdapter;
    List<GioHang> gioHangArrayList;
    private FavoriteDB favoriteDB;
    ThongBao mThongBao;

    private static double tongTienThanhToan = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gio_hang);
        gioHangArrayList = new ArrayList<>();
        gioHangAdapter = new GioHang_Adapter(this, HomeActivity.gioHangArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL,false);
        binding.recylerGioHang.setLayoutManager(layoutManager);
        binding.recylerGioHang.setAdapter(gioHangAdapter);
        favoriteDB = new FavoriteDB(this);
        ActionToolBar();
        CheckData();
        EventUtil();
        OnClickButton();
//        HienThiDaTa();

//        //Luu du lieu
//        DataLocalManager.setListGioHang(HomeActivity.gioHangArrayList);

    }

    private void OnClickButton() {
        binding.btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThongBaoFragment thongBaoFragment = new ThongBaoFragment();
                Utils.MONNEY = tongTienThanhToan;
                Bundle bundle = new Bundle();
                bundle.putDouble("thanhtoan",tongTienThanhToan);
                thongBaoFragment.setArguments(bundle);
                OpenDialog(Gravity.CENTER);
                //Add to sqlite
                String s = String.valueOf(new DecimalFormat("###,###,###.000").format(tongTienThanhToan) + "??");
                String thongBao = "B???n v???a ?????t th??nh c??ng ????n h??ng c?? gi?? tr??? l?? " + s
                        + ". Xin c???m ??n qu?? kh??ch ???? tin t?????ng .";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                String date = currentDate.format(calendar.getTime());
                SimpleDateFormat currentHour = new SimpleDateFormat("HH:mm:ss");
                String hour = currentHour.format(calendar.getTime());
                favoriteDB.insertThongBaoDatabase(thongBao,date,hour);  // cho nay insert vao
            }
        });
    }

    private void OpenDialog(int gravity){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_thanhtoan);
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = gravity;
        window.setAttributes(windowAtributes);
        if (Gravity.CENTER ==gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        Button DialogTiepTucMua = dialog.findViewById(R.id.btnDialogTiepTucMua);

        DialogTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.gioHangArrayList.clear();
                gioHangAdapter.notifyDataSetChanged();
                EventUtil();
                startActivity(new Intent(getBaseContext(),HomeActivity.class));
            }
        });
        dialog.show();
    }

    private void ActionToolBar() {
        setSupportActionBar(binding.tollbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.tollbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void EventUtil() {
        double tongtien = 0;
        double thueVAT = 0;
        for (int i=0;i<HomeActivity.gioHangArrayList.size();i++){
            tongtien += HomeActivity.gioHangArrayList.get(i).getTongGiaSp();
        }
        thueVAT = tongtien*0.1;
        tongTienThanhToan = tongtien+thueVAT;
        binding.txtTongtien.setText(new DecimalFormat("###,###,###.000").format(tongtien) + "??");
        binding.txtVAT.setText(new DecimalFormat("###,###,###.000").format(thueVAT) + "??");
        binding.txtTienThanhToan.setText(new DecimalFormat("###,###,###.000").format(tongTienThanhToan) + "??");

//        DataLocalManager.setListGioHang(HomeActivity.gioHangArrayList);
    }

    private void CheckData() {
        if (HomeActivity.gioHangArrayList.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            binding.recylerGioHang.setVisibility(View.INVISIBLE);
        }else {
            gioHangAdapter.notifyDataSetChanged();
            binding.recylerGioHang.setVisibility(View.VISIBLE);
        }
    }
//    public void HienThiDaTa(){
//        HomeActivity.gioHangArrayList = Database.getInstance(GioHangActivity.this).userDAO().getListGioHang();
//        gioHangAdapter.setDataGioHang(HomeActivity.gioHangArrayList);
//
//    }


}