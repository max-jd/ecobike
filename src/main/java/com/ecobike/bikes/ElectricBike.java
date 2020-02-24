package com.ecobike.bikes;

public class ElectricBike extends Bike {
    protected short maxSpeed;
    protected short batteryCapacity;

    public ElectricBike(String fullName,  short maxSpeed, short weight, boolean frontBackLights, short batteryCapacity,
                        String color, int price) {
        super(fullName, weight, frontBackLights, color, price);
        this.maxSpeed = maxSpeed;
        this.batteryCapacity = batteryCapacity;
    }


    public ElectricBike(){
        maxSpeed = batteryCapacity = -1;
    }

    @Override
    public boolean equals(Object other){
        if(other == this)
            return true;

        if((other == null) || (this.getClass() != other.getClass()))
            return false;

        ElectricBike otherElectricBike = (ElectricBike)other;
        return  otherElectricBike.type.equals(this.type) &&
                otherElectricBike.brand.equals(this.brand) &&
                otherElectricBike.weight == this.weight &&
                otherElectricBike.frontBackLights == this.frontBackLights &&
                otherElectricBike.color.equals(this.color) &&
                otherElectricBike.price == this.price &&
                otherElectricBike.maxSpeed == this.maxSpeed &&
                otherElectricBike.batteryCapacity == this.batteryCapacity;
    }

    @Override
    public String toString(){
        /*
        - A brand
        - The maximum speed (in km/h)
        - The weight of the e-bike (in grams)
        - The availability of lights at front and back (TRUE/FALSE)
        - The battery capacity (in mAh)
        - A color
        - The price

          Example:  E-BIKE Koga; 48; 15488; TRUE; 21000; red; 1899
        */
        String semicolon = "; ";
        return getType().toUpperCase() + " " + brand + semicolon +
                maxSpeed + semicolon +
                weight + semicolon +
                String.valueOf(frontBackLights).toUpperCase() + semicolon +
                batteryCapacity + semicolon +
                color + semicolon +
                price;
    }

    public void setBatteryCapacity(short batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public short getBatteryCapacity() {
        return batteryCapacity;
    }

    public short getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(short maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}
