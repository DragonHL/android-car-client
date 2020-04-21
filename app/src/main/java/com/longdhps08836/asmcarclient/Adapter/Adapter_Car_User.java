package com.longdhps08836.asmcarclient.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.longdhps08836.asmcarclient.ActivityRepairCarUser;
import com.longdhps08836.asmcarclient.DAO.CarUserDAO;
import com.longdhps08836.asmcarclient.Model.Car_user;
import com.longdhps08836.asmcarclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Car_User extends RecyclerView.Adapter<Adapter_Car_User.CommentViewHolder> {

    Context context;
    List<Car_user> userCarList;

    public Adapter_Car_User(Context context, List<Car_user> userCarList) {
        this.context = context;
        this.userCarList = userCarList;

    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItemCarUser;
        TextView txtItemCarNameUser, txtItemCarInforUser;
        ImageView btnEditCarUser, btnDeleteCarUser;

        public CommentViewHolder(View itemView) {
            super(itemView);

            imgItemCarUser = itemView.findViewById(R.id.imgItemCarUser);
            txtItemCarNameUser = itemView.findViewById(R.id.txtItemCarNameUser);
            txtItemCarInforUser = itemView.findViewById(R.id.txtItemCarInforUser);
            btnEditCarUser = itemView.findViewById(R.id.btnEditCarUser);
            btnDeleteCarUser = itemView.findViewById(R.id.btnDeleteCarUser);

        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_car_user, viewGroup, false);

        CommentViewHolder commentViewHolder = new CommentViewHolder(view);


        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, int i) {


        final Car_user car_user = userCarList.get(i);

        holder.txtItemCarNameUser.setText(car_user.getCarName());
        holder.txtItemCarInforUser.setText(car_user.getVehicleMaintenance());

        Picasso.get().load("http://192.168.1.3:3000/uploads/" + car_user.getImg()).into(holder.imgItemCarUser);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(car_user.getImage(), 0 , car_user.getImage().length);
//        holder.imgItemCarUser.setImageBitmap(bitmap);

        Log.d("name", car_user.getCarName());
        Log.d("infor", car_user.getVehicleMaintenance());
        Log.d("images Adapter", String.valueOf(car_user.getImage()));
        Log.d("images Adapter", String.valueOf(car_user.getImg()));


        final CarUserDAO carUserDAO = new CarUserDAO(context);
        holder.btnEditCarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActivityRepairCarUser.class);

                intent.putExtra("idCarUser", car_user.get_id());
                intent.putExtra("nameCarUser", car_user.getCarName());
                intent.putExtra("inforCarUser", car_user.getVehicleMaintenance());
                intent.putExtra("imagesCarUser", car_user.getImg());
                context.startActivity(intent);

            }
        });


        holder.btnDeleteCarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Warning");
                alertDialog.setMessage(" Are you sure you want to delete?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        carUserDAO.delete(car_user.get_id());
                        userCarList.clear();
                        userCarList.addAll(carUserDAO.getAllCarInfor());
                        Toast.makeText(context, "delete successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.create().show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return userCarList.size();

    }

    public void updateAdapter(List<Car_user> userCarList) {
        this.userCarList = userCarList;
        notifyDataSetChanged();
    }
}

