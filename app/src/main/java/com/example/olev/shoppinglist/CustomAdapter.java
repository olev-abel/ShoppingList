package com.example.olev.shoppinglist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(Context context, ArrayList<String> productNames) {
        super(context,R.layout.custom_list_row ,productNames);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView= inflater.inflate(R.layout.custom_list_row,parent,false);

        String singleProductName=getItem(position);
        TextView productName=(TextView)customView.findViewById(R.id.productName);
        productName.setText(singleProductName);


        return customView;
    }
}
