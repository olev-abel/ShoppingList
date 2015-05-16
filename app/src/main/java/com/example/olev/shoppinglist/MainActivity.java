package com.example.olev.shoppinglist;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements DbItemDeleteListener{
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ArrayList<Product> productnames=new ArrayList<>();
    DBHandler dbhandler;
    Toolbar toolbar;

    @Override
    public void delete(final String productId){
        AlertDialog.Builder deleteAlert=new AlertDialog.Builder(this);
        deleteAlert.setMessage("Delete Product?")
                .setPositiveButton("Delete",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        dbhandler.deleteProduct(productId);
                        getProductsFromDb();
                    }
                }).setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle("Delete")
                .create();
        deleteAlert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);




        recyclerView=(RecyclerView)findViewById(R.id.productList);

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Product newProduct= new Product();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("ADD PRODUCT");
                alertDialog.setMessage("Add a new product");

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setHint("Enter product name");
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Add",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        newProduct.set_productname(input.getText().toString());
                        newProduct.set_checked(false);
                        productnames.add(newProduct);
                        dbhandler.addProduct(newProduct);
                        adapter.notifyItemInserted(productnames.size()-1);
                    }
                });

                alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            alertDialog.show();
            }
        });

        dbhandler=new DBHandler(this,null,null,1);
        adapter= new CustomAdapter(this,productnames,this);
        getProductsFromDb();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }



   /* public void addNewProduct(View view){
        EditText userInput=(EditText)findViewById(R.id.userInput);
        userInput.setVisibility(View.VISIBLE);
        String productname=userInput.getText().toString();

        if(productname.equals(""))return;

        Product product=new Product();
        product.set_productname(productname);
        product.set_checked(false);
        productnames.add(product);
        dbhandler.addProduct(product);
        adapter.notifyItemInserted(productnames.size()-1);
        userInput.setText("");

    }*/



    public void getProductsFromDb() {
        productnames.clear();

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
