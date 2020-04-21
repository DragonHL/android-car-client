package com.longdhps08836.asmcarclient.Model;

public class Car_user {
    private String _id;
private String img ;
    private byte[] image;
    private String carName;
    private String vehicleMaintenance;

    public Car_user() {
    }


    public Car_user(String _id, String carName, String vehicleMaintenance) {
        this._id = _id;
        this.carName = carName;
        this.vehicleMaintenance = vehicleMaintenance;
    }

    public Car_user(String carName, String vehicleMaintenance) {
        this.carName = carName;
        this.vehicleMaintenance = vehicleMaintenance;
    }

    public Car_user( String carName, String vehicleMaintenance, byte[] image) {
        this.carName = carName;
        this.vehicleMaintenance = vehicleMaintenance;
        this.image = image;
    }

    public Car_user(String _id, byte[] image, String carName, String vehicleMaintenance) {
        this._id = _id;
        this.image = image;
        this.carName = carName;
        this.vehicleMaintenance = vehicleMaintenance;
    }

    public Car_user(String _id, String img, String carName, String vehicleMaintenance) {
        this._id = _id;
        this.img = img;
        this.carName = carName;
        this.vehicleMaintenance = vehicleMaintenance;
    }



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getVehicleMaintenance() {
        return vehicleMaintenance;
    }

    public void setVehicleMaintenance(String vehicleMaintenance) {
        this.vehicleMaintenance = vehicleMaintenance;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
