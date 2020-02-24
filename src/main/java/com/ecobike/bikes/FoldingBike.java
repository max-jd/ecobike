package com.ecobike.bikes;

public class FoldingBike extends Bike {
    private short sizeWheels;
    private short numberGears;

    public FoldingBike(String fullName, short sizeWheels, short numberGears, short weight,
                       boolean frontBackLights, String color, int price){
        super(fullName, weight, frontBackLights, color, price);
        this.sizeWheels = sizeWheels;
        this.numberGears = numberGears;
    }

    //default values
    public FoldingBike() {
        sizeWheels = numberGears = -1;
    }

    @Override
    public boolean equals(Object other){
        if(other == this)
            return true;

        if((other == null) || (this.getClass() != other.getClass()))
            return false;

        FoldingBike otherFoldingBike = (FoldingBike)other;
        return  otherFoldingBike.type.equals(this.type) &&
                otherFoldingBike.brand.equals(this.brand) &&
                otherFoldingBike.frontBackLights == this.frontBackLights &&
                otherFoldingBike.color.equals(this.color) &&
                otherFoldingBike.price == this.price &&
                otherFoldingBike.numberGears == this.numberGears &&
                otherFoldingBike.sizeWheels == this.sizeWheels;
    }

    @Override
    public String toString(){
        /*
        - A brand
        - The size of the wheels (in inch)
        - The number of gears
        - The weight of the bike (in grams)
        - The availability of lights at front and back (TRUE/FALSE)
        - A color
        - The price

        Example: //FOLDING BIKE Brompton; 20; 6; 9283; TRUE; black; 1199
        */
        String semicolon = "; ";
        return getType().toUpperCase() + " " + brand + semicolon +
                sizeWheels + semicolon +
                numberGears + semicolon +
                weight + semicolon +
                String.valueOf(frontBackLights).toUpperCase() + semicolon +
                color + semicolon +
                price;
    }

    public void setSizeWheels(short sizeWheels) {
        this.sizeWheels = sizeWheels;
    }
    public short getSizeWheels() {
        return sizeWheels;
    }

    public void setNumberGears(short numberGears) {
        this.numberGears = numberGears;
    }
    public short getNumberGears() {
        return numberGears;
    }
}
