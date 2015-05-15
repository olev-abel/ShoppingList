package com.example.olev.shoppinglist;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends Activity  implements DbItemDeleteListener{

    ArrayAdapter<Product> adapter;
    ArrayList<Product> productnames=new ArrayList<>();
    DBHandler dbhandler;


    @Override
    public void delete(String productId){
        dbhandler.deleteProduct(productId);
        getProductsFromDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView productList=(ListView)findViewById(android.R.id.list);
        dbhandler=new DBHandler(this,null,null,1);
        adapter= new CustomAdapter(this,productnames,this);
        getProductsFromDb();
        productList.setAdapter(adapter);


    }



    public void addNewProduct(View view){
        EditText userInput=(EditText)findViewById(R.id.userInput);
        userInput.setVisibility(View.VISIBLE);
        String productname=userInput.getText().toString();

        if(productname.equals(""))return;

        Product product=new Product();
        product.set_productname(productname);
        product.set_checked(false);
        productnames.add(product);
        dbhandler.addProduct(product);
        adapter.notifyDataSetChanged();
        userInput.setText("");

    }



    public void getProductsFromDb() {
        ArrayList<Product> products=dbhandler.getProducts();
        productnames.addAll(products);
        adapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
