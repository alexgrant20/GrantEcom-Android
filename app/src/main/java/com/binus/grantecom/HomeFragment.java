package com.binus.grantecom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ProductHelperClass> productList;

    public void HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvFeaturedProduct);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<ProductHelperClass>> call = apiService.getProducts();

        call.enqueue(new Callback<List<ProductHelperClass>>() {
            @Override
            public void onResponse(Call<List<ProductHelperClass>> call, Response<List<ProductHelperClass>> response) {

                productList = response.body();
                recyclerView.setLayoutManager(layoutManager);
                ProductAdapter recyclerAdapter = new ProductAdapter(productList);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductHelperClass>> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
