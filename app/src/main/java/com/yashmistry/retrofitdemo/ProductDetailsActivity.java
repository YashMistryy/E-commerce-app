package com.yashmistry.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yashmistry.retrofitdemo.Models.Cartproductmodel;
import com.yashmistry.retrofitdemo.Models.productModel;
import com.yashmistry.retrofitdemo.prevalent.CommonIntent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailsActivity extends AppCompatActivity {
    ImageView ivProdImg;
    String id;
    Button btnAddToCart, btnIcr, btnDcr;
//    List<productModel> listProd = new ArrayList<>();
    productModel product;
    private TextView tvProdName, tvProdPrice, tvProdDesc, tvQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        tvProdPrice = findViewById(R.id.tv_productdetail_price);
        tvProdDesc = findViewById(R.id.tv_productdetail_desc);
        tvProdName = findViewById(R.id.tv_productdetail_name);
        ivProdImg = findViewById(R.id.iv_productdetails);
        tvQuantity = findViewById(R.id.tvQuantityDetails);
        btnIcr = findViewById(R.id.btnIcr);
        btnDcr = findViewById(R.id.btnDcr);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        loadProduct();

        btnIcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvQuantity.getText().toString());
                n++;
                tvQuantity.setText(String.valueOf(n));
            }
        });
        btnDcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvQuantity.getText().toString());
                if (n > 0) {
                    n--;
                    tvQuantity.setText(String.valueOf(n));
                }
            }
        });


    }

    private void loadProduct() {
//        load product from products json with product id
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Apis api = retrofit2.create(Apis.class);
        Call<productModel> callGetProductOnly = api.getProductOnly(id);

        callGetProductOnly.enqueue(new Callback<productModel>() {
            @Override
            public void onResponse(Call<productModel> call, Response<productModel> response) {
                if (response.code() != 200) {
                    Toast.makeText(ProductDetailsActivity.this, response.code() + "something went wrong when response was got!", Toast.LENGTH_SHORT).show();
                    return;
                }
                product = response.body();
                tvProdName.setText(product.getProduct_name());
                tvProdDesc.setText(product.getProduct_desc());
                tvProdPrice.setText("₹" + product.getProduct_price());
                Glide.with(ivProdImg).load(product.getProduct_image()).into(ivProdImg);

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        adding whole json object to the cart.json file
                        Cartproductmodel cartproductmodel = new Cartproductmodel(product);
                        cartproductmodel.setProduct_quantity(Integer.parseInt(tvQuantity.getText().toString()));
//                        Toast.makeText(ProductDetailsActivity.this, "qauntity"+cartproductmodel.getProduct_quantity(), Toast.LENGTH_SHORT).show();
                        Call callAddProdToCart = api.addprodToCart(cartproductmodel);
                        callAddProdToCart.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.code() == 500){
//                                    couldn't insert because one entry is already there
//                                    PATCH it with new product with new quantity
//                                    Toast.makeText(ProductDetailsActivity.this, "something came to patch", Toast.LENGTH_SHORT).show();
                                      Call<Cartproductmodel> callPatchTheQuantity = api.editProductInCart(cartproductmodel.getId(),cartproductmodel);
                                      callPatchTheQuantity.enqueue(new Callback<Cartproductmodel>() {
                                          @Override
                                          public void onResponse(Call<Cartproductmodel> call, Response<Cartproductmodel> response) {
                                              Toast.makeText(ProductDetailsActivity.this, "item has been modified", Toast.LENGTH_SHORT).show();
                                              Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                                              startActivity(intent);
                                              return;
                                          }

                                          @Override
                                          public void onFailure(Call<Cartproductmodel> call, Throwable t) {
                                              Toast.makeText(ProductDetailsActivity.this, "failed to modify the item", Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                      return;
                                }
                                if (response.code() != 200) {
                                    Toast.makeText(ProductDetailsActivity.this, "item has been added to the cart", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(ProductDetailsActivity.this, "failure at adding product to cart!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<productModel> call, Throwable t) {

                t.printStackTrace();
                t.getMessage();
            }
        });
    }


}

