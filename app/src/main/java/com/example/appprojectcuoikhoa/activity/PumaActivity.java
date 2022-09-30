package com.example.appprojectcuoikhoa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.databinding.ActivityPumaBinding;

import java.util.ArrayList;
import java.util.List;

import com.example.appprojectcuoikhoa.adapter.Puma_Adapter;
import com.example.appprojectcuoikhoa.model.DataShop;
import com.example.appprojectcuoikhoa.model.Puma;
import com.example.appprojectcuoikhoa.retrofit.ApiInterface;
import com.example.appprojectcuoikhoa.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PumaActivity extends AppCompatActivity {
    ActivityPumaBinding binding;
    ApiInterface apiInterface;
    List<DataShop> dataShopArrayList;
    List<Puma> pumaList;
    Puma_Adapter pumaAdapter;
    String namePuma , imgPuma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_puma);
        dataShopArrayList = new ArrayList<>();
        pumaList = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<DataShop>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<DataShop>>() {
            @Override
            public void onResponse(Call<List<DataShop>> call, Response<List<DataShop>> response) {
                dataShopArrayList = response.body();
                getPumaData(dataShopArrayList.get(0).getPumaList());
            }

            @Override
            public void onFailure(Call<List<DataShop>> call, Throwable t) {
                Toast.makeText(PumaActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.imgPumaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PumaActivity.this, HomeActivity.class));
            }
        });
        Intent intent = getIntent();
        namePuma = intent.getStringExtra("namePuma");
        imgPuma  = intent.getStringExtra("imgPuma");
        Glide.with(this).load(imgPuma).into(binding.imgTitlePuma);
        binding.txtTilePuma.setText(namePuma);
    }
    private void getPumaData(List<Puma> pumaList){
        pumaAdapter = new Puma_Adapter(this,pumaList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerPuma.setLayoutManager(layoutManager);
        binding.recylerPuma.setAdapter(pumaAdapter);
    }
}