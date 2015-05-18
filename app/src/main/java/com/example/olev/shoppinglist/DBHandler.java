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
    public static final String TABLE_NAMES="names";
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
        String query2= "CREATE TABLE " + TABLE_NAMES + " ("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRODUCTNAME + " TEXT UNIQUE"+ ");";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRODUCTS );
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAMES);
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
    public void addName(Product product){
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRODUCTNAME,product.get_productname());
        SQLiteDatabase db=getWritableDatabase();
        db.insertWithOnConflict(TABLE_NAMES,null,values,SQLiteDatabase.CONFLICT_IGNORE);

        db.close();

    }

    public void deleteProduct(String productname){
        SQLiteDatabase db=getWritableDatabase();
        String[] args = {productname};

        db.delete(TABLE_PRODUCTS, COLUMN_PRODUCTNAME + "= ?", args);
    }


    public ArrayList<Product> getProducts(){
        ArrayList<Product> products= new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String query= "SELECT * FROM "+ TABLE_PRODUCTS +";";

        Cursor c= db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("productname"))!=null){
                boolean checked=c.getInt(c.getColumnIndex(COLUMN_ISCHECKED))>0;
                Product product=new Product(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID))),c.getString(c.getColumnIndex(COLUMN_PRODUCTNAME)),checked);
                products.add(product);
            }
            c.moveToNext();
        }
        db.close();
        return products;
    }
    public void changeProduct(Product product){

        ContentValues values= new ContentValues();
        values.put(COLUMN_PRODUCTNAME,product.get_productname());
        values.put(COLUMN_ISCHECKED,product.is_checked());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_PRODUCTS,null,values);
        db.close();
    }

    public void deleteAllProducts(){
        SQLiteDatabase db=getWritableDatabase();
        String query= " DELETE FROM " + TABLE_PRODUCTS + ";";
        db.execSQL(query);
    }

    public  ArrayList<String> getNames(){
        ArrayList<String> names= new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String query= "SELECT DISTINCT "+ COLUMN_PRODUCTNAME+ " FROM " + TABLE_NAMES + ";";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_PRODUCTNAME))!=null){
                names.add(c.getString(c.getColumnIndex(COLUMN_PRODUCTNAME)));
            }
            c.moveToNext();
        }
        db.close();
        return names;
    }
}
