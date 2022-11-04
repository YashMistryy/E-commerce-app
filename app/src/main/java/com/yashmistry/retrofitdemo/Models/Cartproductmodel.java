package com.yashmistry.retrofitdemo.Models;

public class Cartproductmodel {
    String id;
    String product_name;
    String product_price;
    String product_instock;
    String product_image;
    String product_desc;
    int product_quantity;

    public Cartproductmodel(String product_id, String product_name, String product_price, String product_instock, String product_image, String product_desc) {
        this.id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_instock = product_instock;
        this.product_image = product_image;
        this.product_desc = product_desc;
    }

    public Cartproductmodel(productModel productObj) {

        this.id = productObj.getId();
        this.product_name = productObj.getProduct_name();
        this.product_price = productObj.getProduct_price();
        this.product_instock = productObj.getProduct_instock();
        this.product_image = productObj.getProduct_image();
        this.product_desc = productObj.getProduct_desc();
    }

    public Cartproductmodel() {
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }




    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_instock() {
        return product_instock;
    }

    public void setProduct_instock(String product_instock) {
        this.product_instock = product_instock;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
