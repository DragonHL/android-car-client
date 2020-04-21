package com.longdhps08836.asmcarclient.DAO;

import android.content.Context;
import android.util.Log;

import com.longdhps08836.asmcarclient.Fragment_Profile_Client;
import com.longdhps08836.asmcarclient.Model.Car_user;
import com.longdhps08836.asmcarclient.noUi;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import java.util.ArrayList;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


public class CarUserDAO {

    Context context;
    String serverUri = "http://192.168.1.3:3000/";

    ArrayList<Car_user> list = new ArrayList<>();

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(serverUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Emitter.Listener onInsertInforCar = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            Log.d("datainsert", data);
            noUi noUi = new noUi(context);

            if (data == "true") {
                Log.d("insert", "Insert Successfully !");
                noUi.toast("Insert Successfully !");

                noUi.updateListView();
            } else {
                Log.d("insert", "Insert Failed !");
                noUi.toast("Insert Failed !");
            }

        }
    };

    private Emitter.Listener onGetAllInfoCar = new Emitter.Listener() {
        @Override
        public void call(Object... args) {


            noUi noUi = new noUi(context);


            JSONObject jsonObject = (JSONObject) args[0];
            Log.d("jsonObject", String.valueOf(jsonObject));

            try {

                String _id = jsonObject.getString("_id");
                String carName = jsonObject.getString("carName");
                String vehicleMaintenance = jsonObject.getString("vehicleMaintenance");
                String i = jsonObject.getString("images");

                byte[] images = i.getBytes();
                Log.d("images", String.valueOf(images));
                Log.d("i", i);

                Car_user car_user = new Car_user(_id, i, carName, vehicleMaintenance);

                list.add(car_user);

                noUi.updateListView();
                Log.d("carName", String.valueOf(carName));
                Log.d("infor", String.valueOf(vehicleMaintenance));
                Log.d("list", String.valueOf(list));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!list.isEmpty()) {
                Log.d("GetAll", "Get All Successfully!");
                noUi.toast("Get All Successfully!");

            } else {
                Log.d("GetAll", "Get All Failed!");
                noUi.toast("Get All Failed!");
            }

        }
    };


    private Emitter.Listener onUpdateCarUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            Log.d("dataUpdateCarUser", data);
            noUi noUi = new noUi(context);

            if (data == "true") {
                Log.d("updateCarUser", "update CarUser Successfully !");
                noUi.toast("Update Successfully !");

                noUi.updateListView();
            } else {
                Log.d("updateCarUser", "Update CarUser Failed !");
                noUi.toast("Update Failed !");
            }
        }
    };

    private Emitter.Listener onDeleteCarUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            Log.d("data deleteCarUser", data);
            noUi noUi = new noUi(context);

            if (data == "true") {
                Log.d("deleteCarUser", "Delete CarUser Successfully !");
                noUi.toast("Delete Successfully !");

                noUi.updateListView();
            } else {
                Log.d("deleteCarUser", "Delete CarUser Failed !");
                noUi.toast("Delete Failed !");
            }
        }
    };


    public CarUserDAO(Context context) {
        this.context = context;
        mSocket.connect();
        mSocket.on("insertInforCarUser", onInsertInforCar);
        mSocket.on("getAllInfor", onGetAllInfoCar);
        mSocket.on("updateCarUser", onUpdateCarUser);
        mSocket.on("deleteCarUser", onDeleteCarUser);


        Log.d("onGetAllInfoCar", String.valueOf(onGetAllInfoCar));

    }

    public void insert(Car_user carInfor) {
        mSocket.emit("insertInforCarUser", carInfor.getCarName(), carInfor.getVehicleMaintenance(), carInfor.getImage());
    }

    public ArrayList<Car_user> getAllCarInfor() {
        list.clear();

        mSocket.emit("getAllInfor", "Client Android get All infor car");
        return list;
    }

    public void update(Car_user carUserID) {
        mSocket.emit("updateCarUser", carUserID.get_id(), carUserID.getImage(), carUserID.getCarName(), carUserID.getVehicleMaintenance());
    }

    public void delete(String id) {
        mSocket.emit("deleteCarUser", id);
    }

}
