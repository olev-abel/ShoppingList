package com.example.olev.shoppinglist;




import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

class CustomAdapter extends ArrayAdapter<Product>{

    public DbItemDeleteListener deleteListener;

    CustomAdapter(Context context, ArrayList<Product> productNames,DbItemDeleteListener deleteListener) {
        super(context,R.layout.custom_list_row ,productNames);
        this.deleteListener = deleteListener;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView= inflater.inflate(R.layout.custom_list_row,parent,false);

        final Product singleProduct=getItem(position);
        final TextView productName=(TextView)customView.findViewById(R.id.ProductName);
        final Button CheckButton = (Button)customView.findViewById(R.id.CheckButton);
        final Button DeleteButton = (Button)customView.findViewById(R.id.DeleteButton);
        final Drawable original=productName.getBackground();
        productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singleProduct.is_checked()){
                    CheckButton.setText("Uncheck");
                    CheckButton.setVisibility(View.VISIBLE);
                    DeleteButton.setVisibility(View.VISIBLE);
                }
                else{
                CheckButton.setVisibility(View.VISIBLE);
                DeleteButton.setVisibility(View.VISIBLE);
            }
            }
        });
        CheckButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!singleProduct.is_checked()){
               productName.setBackgroundColor(Color.parseColor("#14e715"));
               singleProduct.set_checked(true);

            }
                else{
                    productName.setBackground(original);
                    singleProduct.set_checked(false);
                    CheckButton.setText("Check");
                }
                CheckButton.setVisibility(View.INVISIBLE);
                DeleteButton.setVisibility(View.INVISIBLE);
        }});
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String product=singleProduct.get_productname();
                deleteListener.delete(product);
            }
        });

        productName.setText(singleProduct.get_productname());


        return customView;
    }

}
