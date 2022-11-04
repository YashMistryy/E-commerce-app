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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.yashmistry.retrofitdemo.Models.productModel;
import com.yashmistry.retrofitdemo.ProductDetailsActivity;
import com.yashmistry.retrofitdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder>{
    List<productModel> parraylist;
    Context ctx;

    public ProductRecyclerAdapter(List<productModel> productModelList, Context context) {
        this.ctx = context;
        this.parraylist = productModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(parraylist.get(position).getProduct_name());
        holder.tvPrice.setText("₹ "+parraylist.get(position).getProduct_price());
        Glide.with(holder.ivProd).load(parraylist.get(position).getProduct_image()).into(holder.ivProd);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ProductDetailsActivity.class);
//                bundle data to next activity
                Bundle bundle = new Bundle();
//                bundle.putString("name",parraylist.get(position).getProduct_name());
//                bundle.putString("price",parraylist.get(position).getProduct_price());
//                bundle.putString("image",parraylist.get(position).getProduct_image());
//                bundle.putString("desc",parraylist.get(position).getProduct_desc());
                bundle.putString("id",parraylist.get(position).getId());
                intent.putExtras(bundle);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parraylist.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView ivProd;
        TextView tvName , tvPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProd = itemView.findViewById(R.id.iv_prod_img);
            tvName = itemView.findViewById(R.id.tv_prod_name);
            tvPrice = itemView.findViewById(R.id.tv_prod_price);
        }
    }
}