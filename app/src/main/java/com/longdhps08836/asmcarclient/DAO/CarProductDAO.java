package com.longdhps08836.asmcarclient.DAO;

import android.content.Context;
import android.util.Log;

import com.longdhps08836.asmcarclient.Model.Car_product;

import com.longdhps08836.asmcarclient.noUi;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


public class CarProductDAO {

    Context context;
    String serverUri = "http://192.168.1.3:3000/";

    ArrayList<Car_product> list = new ArrayList<>();

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(serverUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    private Emitter.Listener onGetAllCarProduct = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

//            Car_product car_product = new Car_product();
            noUi noUi = new noUi(context);


            JSONObject jsonObject = (JSONObject) args[0];
            Log.d("jsonObject", String.valueOf(jsonObject));

            try {

                String _id = jsonObject.getString("_id");
                String nameCar = jsonObject.getString("nameCar");
                String priceCar = jsonObject.getString("priceCar");
                String inforCar = jsonObject.getString("inforCar");
                String imagesCar = jsonObject.getString("imagesCar");

                Car_product car_product = new Car_product(_id,nameCar,priceCar,inforCar,imagesCar);

//                car_product.setIdCar(_id);

                list.add(car_product);

                noUi.updateListCarProduct();
                Log.d("_id", String.valueOf(_id));
                Log.d("carName", String.valueOf(nameCar));
                Log.d("priceCar", String.valueOf(priceCar));
                Log.d("inforCar", String.valueOf(inforCar));
                Log.d("imagesCar", String.valueOf(imagesCar));
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

    public CarProductDAO(Context context) {
        this.context = context;
        mSocket.connect();
        mSocket.on("getAllCarProduct", onGetAllCarProduct);

        Log.d("onGetAllInfoCar", String.valueOf(onGetAllCarProduct));

    }

    public ArrayList<Car_product> getAllCarProduct() {
        list.clear();
        mSocket.emit("getAllCarProduct", "Client Android get All Car Product");
        return list;
    }



}
