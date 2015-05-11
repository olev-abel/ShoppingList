package com.example.olev.shoppinglist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<Product> {

    CustomAdapter(Context context, ArrayList<Product> productNames) {
        super(context,R.layout.custom_list_row ,productNames);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView= inflater.inflate(R.layout.custom_list_row,parent,false);

        Product singleProduct=getItem(position);
        TextView productName=(TextView)customView.findViewById(R.id.ProductName);
        /*Button CheckButton = (Button)customView.findViewById(R.id.CheckButton);
        Button DeleteButton = (Button)customView.findViewById(R.id.DeleteButton);
        CheckButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                singleProduct.set_checked(true);
            }
        });
        DeleteButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

            }
        });*/
        productName.setText(singleProduct.get_productname());


        return customView;
    }
}
