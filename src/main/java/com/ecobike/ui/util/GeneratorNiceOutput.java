package com.ecobike.ui.util;

import com.ecobike.bikes.Bike;
import com.ecobike.bikes.ElectricBike;
import com.ecobike.bikes.FoldingBike;
import com.ecobike.bikes.Speedelec;

public class GeneratorNiceOutput {
    public static String generateBike(Bike bike) {
        if(bike instanceof FoldingBike){
            FoldingBike foldingBike = (FoldingBike) bike;
            String niceDescription = foldingBike.getType() + " " + foldingBike.getBrand() + " with " +
                    foldingBike.getNumberGears() + " gear(s) and " +
                    (foldingBike.isFrontBackLights() ? " " : "no ") + "head/tail light." + System.lineSeparator() +
                    "Price: " + foldingBike.getPrice() + " euros.";
            return niceDescription;

        } else if(bike instanceof Speedelec) {
            Speedelec speedelec = (Speedelec)bike;
            String niceDescription = speedelec.getType() + " " + speedelec.getBrand() + " with " +
                    speedelec.getBatteryCapacity() + " mAh battery and " +
                    (speedelec.isFrontBackLights() ? " " : "no ") + "head/tail light." + System.lineSeparator() +
                    "Price: " + speedelec.getPrice() + " euros.";
            return niceDescription;

        }//ElectricBike must be last because of inheritance
        else if(bike instanceof ElectricBike) {
            ElectricBike electricBike = (ElectricBike)bike;
            String niceDescription = electricBike.getType() + " " + electricBike.getBrand() + " with " +
                    electricBike.getBatteryCapacity() + " mAh battery and " +
                    (electricBike.isFrontBackLights() ? " " : "no ") + "head/tail light." + System.lineSeparator() +
                    "Price: " + electricBike.getPrice() + " euros.";
            return niceDescription;
        }
        return null;
    }
}
