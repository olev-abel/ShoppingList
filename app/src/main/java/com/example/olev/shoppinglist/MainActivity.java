package com.example.olev.shoppinglist;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.melnykov.fab.FloatingActionButton;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements DbItemDeleteListener,DbItemChangeListener{
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ArrayList<Product> productnames=new ArrayList<>();
    ArrayList<String> names=new ArrayList<>();
    DBHandler dbhandler;
    Toolbar toolbar;
    ArrayAdapter<String> autoCompleteAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.productList);
        autoCompleteAdapter= new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,names);


        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Product newProduct = new Product();
                final Dialog addProductDialog = new Dialog(MainActivity.this);
                addProductDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addProductDialog.setContentView(R.layout.add_product_dialog);
                final AutoCompleteTextView userInput = (AutoCompleteTextView) addProductDialog.findViewById(R.id.userInput);

                userInput.setAdapter(autoCompleteAdapter);
                getNamesFromDb();
                userInput.setThreshold(2);

                Button addButton = (Button) addProductDialog.findViewById(R.id.AddProductDialogAddButton);
                Button cancelButton = (Button) addProductDialog.findViewById(R.id.AddProductDialogCancelButton);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newProduct.set_productname(userInput.getText().toString());
                        newProduct.set_checked(newProduct.is_checked());
                        productnames.add(newProduct);
                        autoCompleteAdapter.add(newProduct.get_productname());
                        dbhandler.addProduct(newProduct);
                        dbhandler.addName(newProduct);
                        autoCompleteAdapter.notifyDataSetChanged();

                        adapter.notifyItemInserted(productnames.size() - 1);
                        addProductDialog.dismiss();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addProductDialog.dismiss();
                    }
                });

                addProductDialog.show();
            }
        });

        dbhandler=new DBHandler(this,null,null,1);
        adapter= new CustomAdapter(this,productnames,this,this);
        getProductsFromDb();


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void getNamesFromDb(){
        names.clear();
        ArrayList<String> n=dbhandler.getNames();
        names.addAll(n);
        Log.v("lamp",Integer.toString(names.size()));
        autoCompleteAdapter.notifyDataSetChanged();
    }

    public void getProductsFromDb() {

        productnames.clear();
        ArrayList<Product> products=dbhandler.getProducts();
        productnames.addAll(products);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void delete(final String productname){
        AlertDialog.Builder deleteAlert=new AlertDialog.Builder(this);
        deleteAlert.setMessage("Delete Product?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbhandler.deleteProduct(productname);
                        getProductsFromDb();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .create();
        deleteAlert.show();

    }

    @Override
    public void change(Product product) {
        dbhandler.deleteProduct(product.get_productname());
        dbhandler.changeProduct(product);

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
        if(id==R.id.DeleteList){
            AlertDialog.Builder deleteAlert=new AlertDialog.Builder(this);
            deleteAlert.setMessage("Delete All Products?")
                    .setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbhandler.deleteAllProducts();
                            productnames.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
                    .create();
            deleteAlert.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
