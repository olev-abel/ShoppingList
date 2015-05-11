package com.example.olev.shoppinglist;


import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ArrayAdapter<Product> adapter;
    ArrayList<Product> productnames=new ArrayList<>();
    DBHandler dbhandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView productList=(ListView)findViewById(R.id.productList);
        dbhandler=new DBHandler(this,null,null,1);
       // getProductsFromDb();
        adapter= new CustomAdapter(this,productnames);
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
        //getProductsFromDb();
    }

    public void getProductsFromDb() {
            ArrayList<Product> products=dbhandler.getProductsFromDb();
            productnames.addAll(products);
            adapter.notifyDataSetChanged();
    }
    public void deleteProduct(View view){

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
