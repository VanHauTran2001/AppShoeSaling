package com.example.appprojectcuoikhoa.retrofit;

import java.util.List;

import com.example.appprojectcuoikhoa.model.DataShop;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("datashop")
    Call<List<DataShop>> getAllData();
}
