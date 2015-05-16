package com.example.olev.shoppinglist;




import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;


class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    List<Product> products= Collections.emptyList();
    public DbItemDeleteListener deleteListener;


    public CustomAdapter(Context context,List<Product> products,DbItemDeleteListener deleteListener){
        inflater=LayoutInflater.from(context);
        this.products=products;
        this.deleteListener = deleteListener;

    }

     public void delete(int position){
         products.remove(position);
         notifyItemRemoved(position);
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
                    if(checkIcon.getVisibility()==View.VISIBLE){
                        checkIcon.setVisibility(View.GONE);
                        checkButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                        checkButton.setText("Check");

                    }else{
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

                }
            });

        }
    }

}
