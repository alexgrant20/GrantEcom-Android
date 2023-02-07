package com.binus.grantecom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiInterface {

    @GET("products")
    Call<List<ProductHelperClass>> getProducts();
}

