package com.yashmistry.retrofitdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yashmistry.retrofitdemo.Adapers.ProductRecyclerAdapter;
import com.yashmistry.retrofitdemo.Models.StoreModel;
import com.yashmistry.retrofitdemo.Models.productModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<productModel> parraylist = new ArrayList<>();
    RecyclerView rvProducts;
    StoreModel storeModel;
    TextView tvStoreName, tvStoreCategory, tvStoreAddress;
    FloatingActionButton btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvProducts = findViewById(R.id.rvProducts);
        tvStoreAddress = findViewById(R.id.tvStoreAddress);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvStoreCategory = findViewById(R.id.tvStoreCategory);
        btnCart = findViewById(R.id.cartBtn);
        loadHome();
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadHome() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Apis api = retrofit.create(Apis.class);

        Call<ArrayList<productModel>> callProduct = api.getProducts();
        callProduct.enqueue(new Callback<ArrayList<productModel>>() {
            @Override
            public void onResponse(Call<ArrayList<productModel>> call, Response<ArrayList<productModel>> response) {
//                Toast.makeText(MainActivity.this, "call response has been recieved", Toast.LENGTH_SHORT).show();
                if (response.code() != 200) {
//                    Toast.makeText(MainActivity.this, "not successfull", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<productModel> list = response.body();
                for (productModel prod : list) {
                    parraylist.add(prod);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<productModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });

        Call<StoreModel> callStoreDetails = api.getStoreDetail();
        callStoreDetails.enqueue(new Callback<StoreModel>() {
            @Override
            public void onResponse(Call<StoreModel> call, Response<StoreModel> response) {
                if ((response.code() != 200)) {
                    return;
                }
                loadStoreDetails(response.body());
            }

            @Override
            public void onFailure(Call<StoreModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "store details couldn't be fetched", Toast.LENGTH_SHORT).show();
            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(new ProductRecyclerAdapter(parraylist, MainActivity.this));

    }


    private void loadStoreDetails(StoreModel response) {
        storeModel = new StoreModel(response);
        StoreModel store = response;
        storeModel.setName(store.getName());
        storeModel.setAddress(store.getAddress());
        storeModel.setCategory(store.getCategory());
        tvStoreAddress.setText(store.getAddress());
        tvStoreName.setText(store.getName());
        tvStoreCategory.setText(store.getCategory());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit!")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
