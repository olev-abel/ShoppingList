package com.example.olev.shoppinglist;


public class Product {

    private int _id;
    private String _productname;
    private boolean _checked;

    public Product(int _id, String _productname, boolean _checked) {
        this._id = _id;
        this._productname = _productname;
        this._checked = _checked;
    }

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
    public boolean is_checked() {
        return _checked;
    }

    public void set_checked(boolean _checked) {
        this._checked = _checked;
    }
}
