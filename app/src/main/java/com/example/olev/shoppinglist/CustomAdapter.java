package com.example.olev.shoppinglist;




import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import java.util.Collections;
import java.util.List;


class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    List<Product> products= Collections.emptyList();
    public DbItemDeleteListener deleteListener;
    public DbItemChangeListener changeListener;


    public CustomAdapter(Context context,List<Product> products,DbItemDeleteListener deleteListener,DbItemChangeListener changeListener){
        inflater=LayoutInflater.from(context);
        this.products=products;
        this.deleteListener = deleteListener;
        this.changeListener = changeListener;

    }



    public void check(int position){
        Product product=products.get(position);
        product.set_checked(true);
    }
    public void uncheck(int position){
        Product product=products.get(position);
        product.set_checked(false);
    }

    public Product getProduct(int position){
        Product product=products.get(position);
        return product;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_list_row,parent,false);
        MyViewHolder holder=new MyViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product currentProduct=products.get(position);
        holder.productname.setText(currentProduct.get_productname());
        if(currentProduct.is_checked()) {
            holder.checkIcon.setVisibility(View.VISIBLE);
        }
        else {
            holder.checkIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView productname;
        Button checkButton;
        Button deleteButton;
        ImageView checkIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            productname= (TextView) itemView.findViewById(R.id.ProductName);
            checkButton= (Button) itemView.findViewById(R.id.checkButton);
            deleteButton= (Button) itemView.findViewById(R.id.deleteButton);
            checkIcon= (ImageView) itemView.findViewById(R.id.checkIcon);




        }


        @Override
        public void onClick(View v) {
            if (checkButton.getVisibility() == View.VISIBLE) {
                checkButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
            } else if(checkIcon.getVisibility()==View.VISIBLE) {
                checkButton.setText("Uncheck");
                checkButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);

            }else{
                checkButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
            }


            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getProduct(getPosition()).is_checked()){
                        uncheck(getPosition());
                        changeListener.change(getProduct(getPosition()));
                        checkIcon.setVisibility(View.GONE);
                        checkButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                        checkButton.setText("Check");

                    }else{
                        check(getPosition());
                        changeListener.change(getProduct(getPosition()));
                        checkIcon.setVisibility(View.VISIBLE);
                        checkButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                }
                }

            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.delete(productname.getText().toString());
                    checkButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);

                }
            });

        }
    }

}
