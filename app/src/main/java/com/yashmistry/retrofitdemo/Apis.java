package com.yashmistry.retrofitdemo;

import com.yashmistry.retrofitdemo.Models.Cartproductmodel;
import com.yashmistry.retrofitdemo.Models.StoreModel;
import com.yashmistry.retrofitdemo.Models.orderedProdModel;
import com.yashmistry.retrofitdemo.Models.productModel;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apis {

    @GET("/products")
    Call<ArrayList<productModel>> getProducts();

    @GET("/store")
    Call<StoreModel> getStoreDetail();

    @GET("products/{pid}")
    Call<productModel> getProductOnly(@Path("pid") String product_id);

    @POST("/cart")
    Call<Cartproductmodel> addprodToCart(@Body Cartproductmodel cartproductmodel);

    @GET("/cart")
    Call<ArrayList<Cartproductmodel>> getCartList();

    @PATCH("cart/{id}")
    Call<Cartproductmodel> editProductInCart(@Path("id")String pid,@Body Cartproductmodel cartproductmodel);

    @DELETE("cart/{id}")
    Call<String> deleteProduct(@Path("id")String pid);

    @POST("/orders")
    Call<ArrayList<orderedProdModel>> confirmOrder(@Body ArrayList<orderedProdModel> ProdModellist);

    @POST("/order_details")
    Call<String> sendOrderDetails(@Body String str);
//
//    @POST("/cart")
//    Call<ArrayList<Cartproductmodel>> deleteCart(@Body ArrayList<Cartproductmodel> cartproductmodel);
 }
