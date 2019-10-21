package com.cashmanager.model;

import java.util.ArrayList;

public class Total {
    private int number_of_product;
    private ArrayList<Product> list_of_product;
    private int total_price;

    public Total(int id_order, int number_of_product, int total_price) {
        this.number_of_product = 0;
        this.total_price = 0;
    }

    public void add_product(Product prod) {
        number_of_product++;
        total_price += prod.getPrice();
        list_of_product.add(prod);
    }

    public void delete_product(Product prod) {
        number_of_product--;
        total_price -= prod.getPrice();
        list_of_product.remove(prod);
    }

    public int getNumber_of_product() {
        return number_of_product;
    }

    public ArrayList<Product> getList_of_product() {
        return list_of_product;
    }

    public int getTotal_price() {
        return total_price;
    }
}
