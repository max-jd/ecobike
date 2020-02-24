package com.ecobike.ui.util;

import com.ecobike.bikes.Bike;
import com.ecobike.bikes.ElectricBike;
import com.ecobike.bikes.FoldingBike;
import com.ecobike.bikes.Speedelec;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratorNiceOutputTest {
    @Test
    public void generateFoldingBike() {
        Bike bike = new FoldingBike("FOLDING BIKE Titan", (short)20, (short)1 , (short)11800, false, "khaki", 1115);
        assertTrue(GeneratorNiceOutput.generateBike(bike).equals("FOLDING BIKE Titan with 1 gear(s) and no head/tail light." + System.lineSeparator() +
                "Price: 1115 euros."));
    }

    @Test
    public void generateSpeedelec() {
        Bike bike = new Speedelec("SPEEDELEC E-Scooter", (short)60, (short)15300, false, (short)14800, "marine", 309);
        assertTrue(GeneratorNiceOutput.generateBike(bike).equals("SPEEDELEC E-Scooter with 14800 mAh battery and no head/tail light." + System.lineSeparator() +
                "Price: 309 euros."));
    }

    @Test
    public void generateElectricBike() {
        Bike bike = new ElectricBike("E-BIKE Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        assertTrue(GeneratorNiceOutput.generateBike(bike).equals("E-BIKE Lankeleisi with 10000 mAh battery and no head/tail light." + System.lineSeparator() +
                "Price: 2399 euros."));
    }
}