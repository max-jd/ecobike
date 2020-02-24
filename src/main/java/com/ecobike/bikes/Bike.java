package com.ecobike.bikes;

public abstract class Bike implements Comparable<Bike>{
    protected String type;
    protected String brand;
    protected short weight;
    protected Boolean frontBackLights;
    protected String color;
    protected int price;

    protected Bike(String fullName, short weight, boolean frontBackLights, String color, int price) {
        //decompose the full name
        int spaceLastIndex = fullName.lastIndexOf(' ');
        type = fullName.substring(0, spaceLastIndex);
        this.brand = fullName.substring(spaceLastIndex + 1);

        this.weight = weight;
        this.frontBackLights = frontBackLights;
        this.color = color;
        this.price = price;
    }

    //default values
    public Bike(){
        price = weight = -1;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean isFrontBackLights() {
        return frontBackLights;
    }
    public void setFrontBackLights(Boolean frontBackLights) {
        this.frontBackLights = frontBackLights;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public short getWeight() {
        return weight;
    }
    public void setWeight(short weight) {
        this.weight = weight;
    }

    //every subclass must to override his own equals()
    @Override
    public abstract boolean equals(Object other);

    //every subclass must to override his own toString()
    @Override
    public abstract String toString();

    public int compareTo(Bike anotherBike){
        return this.toString().compareTo(anotherBike.toString());
    }
}
