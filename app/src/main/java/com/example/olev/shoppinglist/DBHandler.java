package com.example.olev.shoppinglist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler  extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="products.db";
    public static final String TABLE_PRODUCTS= "products";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_PRODUCTNAME="productname";
    public static final String COLUMN_ISCHECKED="ischecked";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_PRODUCTS + " ("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRODUCTNAME + " TEXT," +
                COLUMN_ISCHECKED + " BOOLEAN" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRODUCTS );
        onCreate(db);
    }

    public  void addProduct(Product product){
        ContentValues values= new ContentValues();
        values.put(COLUMN_PRODUCTNAME,product.get_productname());
        values.put(COLUMN_ISCHECKED,product.is_checked());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_PRODUCTS,null,values);
        db.close();
    }
    public void deleteProduct(String productname){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_PRODUCTS+ " WHERE "+ COLUMN_PRODUCTNAME+ "=\" "+ productname +"\";");
        }

    public ArrayList<Product> getProducts(){
        ArrayList<Product> products= new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String query= "SELECT * FROM "+ TABLE_PRODUCTS +";";

        Cursor c= db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("productname"))!=null){
                Product product=new Product(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID))),c.getString(c.getColumnIndex(COLUMN_PRODUCTNAME)),Boolean.valueOf(c.getString(c.getColumnIndex(COLUMN_ISCHECKED))));
                products.add(product);
            }
            c.moveToNext();
        }
        db.close();
        return products;
    }
}
