package com.yashmistry.retrofitdemo.Adapers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.yashmistry.retrofitdemo.Apis;
import com.yashmistry.retrofitdemo.CartActivity;
import com.yashmistry.retrofitdemo.Models.Cartproductmodel;
import com.yashmistry.retrofitdemo.Models.productModel;
import com.yashmistry.retrofitdemo.ProductDetailsActivity;
import com.yashmistry.retrofitdemo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>{
    List<Cartproductmodel> parraylist;
    Context ctx;

    public CartListAdapter(List<Cartproductmodel> productModelList, Context context) {
        this.ctx = context;
        this.parraylist = productModelList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_product_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(parraylist.get(position).getProduct_name());
        holder.tvPrice.setText("₹ "+parraylist.get(position).getProduct_price());
        holder.tvquantity.setText("X"+parraylist.get(position).getProduct_quantity());
        Glide.with(holder.ivProd).load(parraylist.get(position).getProduct_image()).into(holder.ivProd);

        holder.ivDeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                delete the product from list
                Retrofit retrofit3 = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Toast.makeText(ctx, parraylist.get(position).getId(), Toast.LENGTH_SHORT).show();
                Apis api = retrofit3.create(Apis.class);
                Call<String> callToDeleteProd = api.deleteProduct(parraylist.get(position).getId());
                callToDeleteProd.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call call, Response response) {

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });

                Intent intent = new Intent(ctx, CartActivity.class);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parraylist.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView ivProd, ivDeletebtn;
        TextView tvName , tvPrice, tvquantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProd = itemView.findViewById(R.id.cartimageView);
            tvName = itemView.findViewById(R.id.tv_cart_prod_name);
            tvPrice = itemView.findViewById(R.id.tv_cart_prod_price);
            tvquantity = itemView.findViewById(R.id.tv_cart_quantity);
            ivDeletebtn = itemView.findViewById(R.id.ivDeleteImg);
        }
    }
}