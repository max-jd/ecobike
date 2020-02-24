package com.ecobike.bikes;

public class Speedelec extends ElectricBike {
    public Speedelec(String fullName, short maxSpeed, short weight, boolean frontBackLights,
                     short batteryCapacity, String color, int price){
        super(fullName, maxSpeed, weight, frontBackLights, batteryCapacity, color, price);
    }

    public Speedelec(){}

    @Override
    public boolean equals(Object other){
        //if other == null or to another class, then false
        if(other instanceof Speedelec){
            return super.equals(other);
        }
        return false;
    }
}
