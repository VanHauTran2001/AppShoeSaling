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
import com.example.appprojectcuoikhoa.databinding.ActivityNikeBinding;

import java.util.ArrayList;
import java.util.List;

import com.example.appprojectcuoikhoa.adapter.Adidas_Adapter;
import com.example.appprojectcuoikhoa.adapter.Gucci_Adapter;
import com.example.appprojectcuoikhoa.adapter.Nike_Adapter;
import com.example.appprojectcuoikhoa.adapter.Puma_Adapter;
import com.example.appprojectcuoikhoa.adapter.Vans_Adapter;
import com.example.appprojectcuoikhoa.model.Adidas;
import com.example.appprojectcuoikhoa.model.DataShop;
import com.example.appprojectcuoikhoa.model.Gucci;
import com.example.appprojectcuoikhoa.model.Nike;
import com.example.appprojectcuoikhoa.model.Puma;
import com.example.appprojectcuoikhoa.model.Vans;
import com.example.appprojectcuoikhoa.retrofit.ApiInterface;
import com.example.appprojectcuoikhoa.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NikeActivity extends AppCompatActivity {
    ActivityNikeBinding binding;
    ApiInterface apiInterface;
    List<DataShop> dataShopArrayList;
    List<Nike> nikeList;
    Nike_Adapter nikeAdapter;
    Gucci_Adapter gucciAdapter;
    Puma_Adapter pumaAdapter;
    Vans_Adapter vansAdapter;
    Adidas_Adapter adidasAdapter;
    String nameNike , imgNike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this, R.layout.activity_nike);
        dataShopArrayList = new ArrayList<>();
        nikeList = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<DataShop>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<DataShop>>() {
            @Override
            public void onResponse(Call<List<DataShop>> call, Response<List<DataShop>> response) {
                dataShopArrayList = response.body();
                switch (nameNike){
                    case "Nike":
                        getNikeData(dataShopArrayList.get(0).getNikeList());
                        break;
                    case "Adidas":
                        getAdidasData(dataShopArrayList.get(0).getAdidasList());
                        break;
                    case "Gucci":
                        getGucciData(dataShopArrayList.get(0).getGucciList());
                        break;
                    case "Puma":
                        getPumaData(dataShopArrayList.get(0).getPumaList());
                        break;
                    case "Vans":
                        getVansData(dataShopArrayList.get(0).getVansList());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<DataShop>> call, Throwable t) {
                Toast.makeText(NikeActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.imgNikeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NikeActivity.this, HomeActivity.class));
            }
        });
        //Nhan du lieu
        Intent intent = getIntent();
        nameNike = intent.getStringExtra("name");
        imgNike  = intent.getStringExtra("image");
        Glide.with(this).load(imgNike).into(binding.imgTitleNike);
        binding.txtTileNike.setText(nameNike);
    }
    private void getNikeData(List<Nike> nikeList){
        nikeAdapter = new Nike_Adapter(this,nikeList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerNike.setLayoutManager(layoutManager);
        binding.recylerNike.setAdapter(nikeAdapter);
        nikeAdapter.notifyDataSetChanged();
    }
    private void getAdidasData(List<Adidas> adidasList){
        adidasAdapter = new Adidas_Adapter(this,adidasList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerNike.setLayoutManager(layoutManager);
        binding.recylerNike.setAdapter(adidasAdapter);
        adidasAdapter.notifyDataSetChanged();
    }
    private void getGucciData(List<Gucci> gucciList){
        gucciAdapter = new Gucci_Adapter(this,gucciList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerNike.setLayoutManager(layoutManager);
        binding.recylerNike.setAdapter(gucciAdapter);
        gucciAdapter.notifyDataSetChanged();
    }
    private void getPumaData(List<Puma> pumaList){
        pumaAdapter = new Puma_Adapter(this,pumaList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerNike.setLayoutManager(layoutManager);
        binding.recylerNike.setAdapter(pumaAdapter);
        pumaAdapter.notifyDataSetChanged();
    }
    private void getVansData(List<Vans> vansList){
        vansAdapter = new Vans_Adapter(this,vansList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recylerNike.setLayoutManager(layoutManager);
        binding.recylerNike.setAdapter(vansAdapter);
        vansAdapter.notifyDataSetChanged();
    }
}