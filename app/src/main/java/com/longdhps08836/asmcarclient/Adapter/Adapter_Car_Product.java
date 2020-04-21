package com.longdhps08836.asmcarclient.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longdhps08836.asmcarclient.Model.Car_product;

import com.longdhps08836.asmcarclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Car_Product extends RecyclerView.Adapter<Adapter_Car_Product.CommentViewHolder> {

    Context context;
    List<Car_product> carProductList;



    public Adapter_Car_Product(Context context, List<Car_product> carProductList) {
        this.context = context;
        this.carProductList = carProductList;

    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItemCarProduct;
        TextView txtItemCarNameProduct;
        TextView txtItemCarPrice;
        TextView txtItemCarInfor;

        public CommentViewHolder(View itemView) {
            super(itemView);

            imgItemCarProduct = itemView.findViewById(R.id.imgItemCarProduct);
            txtItemCarNameProduct = itemView.findViewById(R.id.txtItemCarNameProduct);
            txtItemCarPrice = itemView.findViewById(R.id.txtItemCarPrice);
            txtItemCarInfor = itemView.findViewById(R.id.txtItemCarInfor);

        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_car_product, viewGroup, false);

        CommentViewHolder commentViewHolder = new CommentViewHolder(view);


        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int i) {

        Car_product car_product = carProductList.get(i);

        holder.txtItemCarNameProduct.setText(car_product.getNameCar());
        holder.txtItemCarPrice.setText(car_product.getPriceCar());

        holder.txtItemCarInfor.setText(car_product.getInforCar());
        Picasso.get().load("http://192.168.1.3:3000/uploads/" + car_product.getImagesCar()).into(holder.imgItemCarProduct);

        Log.d("name", car_product.getNameCar());
        Log.d("price", car_product.getPriceCar());
        Log.d("infor", car_product.getInforCar());
        Log.d("image", car_product.getImagesCar());

    }

    @Override
    public int getItemCount() {
        return carProductList.size();
    }
}

