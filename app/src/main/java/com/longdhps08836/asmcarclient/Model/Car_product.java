package com.longdhps08836.asmcarclient.Model;

public class Car_product {

    private String idCar;
    private String nameCar;
    private String priceCar;
    private String inforCar;
    private String imagesCar;

    public Car_product() {
    }

    public Car_product(String nameCar, String priceCar) {
        this.nameCar = nameCar;
        this.priceCar = priceCar;
    }

    public Car_product(String nameCar, String priceCar, String imagesCar) {
        this.nameCar = nameCar;
        this.priceCar = priceCar;
        this.imagesCar = imagesCar;
    }

    public Car_product(String nameCar, String priceCar, String inforCar, String imagesCar) {
        this.nameCar = nameCar;
        this.priceCar = priceCar;
        this.inforCar = inforCar;
        this.imagesCar = imagesCar;
    }

    public Car_product(String idCar, String nameCar, String priceCar, String inforCar, String imagesCar) {
        this.idCar = idCar;
        this.nameCar = nameCar;
        this.priceCar = priceCar;
        this.inforCar = inforCar;
        this.imagesCar = imagesCar;
    }

    public String getIdCar() {
        return idCar;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
    }

    public String getNameCar() {
        return nameCar;
    }

    public void setNameCar(String nameCar) {
        this.nameCar = nameCar;
    }

    public String getPriceCar() {
        return priceCar;
    }

    public void setPriceCar(String priceCar) {
        this.priceCar = priceCar;
    }

    public String getInforCar() {
        return inforCar;
    }

    public void setInforCar(String inforCar) {
        this.inforCar = inforCar;
    }

    public String getImagesCar() {
        return imagesCar;
    }

    public void setImagesCar(String imagesCar) {
        this.imagesCar = imagesCar;
    }
}
