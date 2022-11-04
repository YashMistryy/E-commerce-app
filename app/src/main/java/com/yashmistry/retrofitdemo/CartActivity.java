package com.yashmistry.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yashmistry.retrofitdemo.Adapers.CartListAdapter;
import com.yashmistry.retrofitdemo.Models.Cartproductmodel;
import com.yashmistry.retrofitdemo.Models.orderedProdModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    private TextView tvCartprodPrice;
    EditText etAddress;
    Button btnCheckout;
    ArrayList<Cartproductmodel> cartlist = new ArrayList<>();
    RecyclerView rvCartList;
    ArrayList<orderedProdModel> olist = new ArrayList<>();
//    ArrayList<Cartproductmodel> del = new ArrayList<>();
    int total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tvCartprodPrice = findViewById(R.id.tvResultPrice);
        rvCartList = findViewById(R.id.rvCartProducts);
        btnCheckout = findViewById(R.id.btnCheckout);
        etAddress = findViewById(R.id.etAddress);
        loadCartList();
        setRecyclerAdapter();
        
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //checkout with user details like address
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Apis api = retrofit.create(Apis.class);
                for (Cartproductmodel cartproductmodel:cartlist) {
                    orderedProdModel o = new orderedProdModel(cartproductmodel,etAddress.getText().toString());
                    olist.add(o);

                }
                Call<ArrayList<orderedProdModel>> callToConfirmOrder = api.confirmOrder(olist);
                callToConfirmOrder.enqueue(new Callback<ArrayList<orderedProdModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<orderedProdModel>> call, Response<ArrayList<orderedProdModel>> response) {
                        Toast.makeText(CartActivity.this, "order has been placed", Toast.LENGTH_SHORT).show();
                        emptyCart();
                        Intent intent = new Intent(CartActivity.this,SuccessScreenActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<orderedProdModel>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                






            }
        });
    }

    private void emptyCart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Apis api = retrofit.create(Apis.class);
        String[] idArray = new String[cartlist.size()];

        for (Cartproductmodel cartproductmodel:cartlist) {

            Call<String> callToDeleteProd = api.deleteProduct(cartproductmodel.getId());
            callToDeleteProd.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(CartActivity.this, "cart has been emptied", Toast.LENGTH_SHORT).show();              
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });}
    }

    private void setRecyclerAdapter() {
        CartListAdapter cartListAdapter = new CartListAdapter(cartlist,CartActivity.this);
        rvCartList.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        rvCartList.setAdapter(cartListAdapter);
    }

    private void loadCartList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Apis api = retrofit.create(Apis.class);
        Call<ArrayList<Cartproductmodel>> callCartList = api.getCartList();
        callCartList.enqueue(new Callback<ArrayList<Cartproductmodel>>() {
            @Override
            public void onResponse(Call<ArrayList<Cartproductmodel>> call, Response<ArrayList<Cartproductmodel>> response) {
                if(response.code()!=200){
                    Toast.makeText(CartActivity.this, "something went wrong while fetching cart list", Toast.LENGTH_SHORT).show();return;}
                ArrayList<Cartproductmodel> cartproductlist = response.body();
                for (Cartproductmodel cartproductmodel:cartproductlist ) {
                    cartlist.add(cartproductmodel);
                    total += Integer.parseInt(cartproductmodel.getProduct_price())*cartproductmodel.getProduct_quantity();
                }
                tvCartprodPrice.setText("Total : ₹"+String.valueOf(total));
            }

            @Override
            public void onFailure(Call<ArrayList<Cartproductmodel>> call, Throwable t) {
            t.printStackTrace();
            }
        });

    }
}