package com.example.olev.shoppinglist;


public class Product {

    private int _id;
    private String _productname;

    public Product(String _productname) {
        this._productname = _productname;
    }

    public Product() {
    }

    public String get_productname() {
        return _productname;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }
}
