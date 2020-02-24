package com.ecobike.bikes;

import org.junit.Test;

import static org.junit.Assert.*;

public class ElectricBikeTest {
    @Test
    public void equalsTwoElectricBikes() {
        Bike bike1 = new ElectricBike("E-BIKE Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        Bike bike2 = new ElectricBike("E-BIKE Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        assertTrue(bike1.equals(bike2));
    }

    @Test
    public void equalsElectricWithFoldingBikes() {
        Bike bike1 = new ElectricBike("E-BIKE Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        Bike bike2 = new FoldingBike("E-BIKE Lankeleisi", (short)65, (short)24200, (short)10000,  false, "black", 2399);
        assertFalse(bike1.equals(bike2));
    }

    @Test
    public void equalsElectricWithSpeedlec() {
        Bike bike1 = new ElectricBike("E-BIKE Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        Bike bike2 = new Speedelec("E-BIKE Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        assertFalse(bike1.equals(bike2));
    }

    @Test
    public void toStringCorrectOutput() {
        Bike bike = new ElectricBike("Electric Bike Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        assertTrue(bike.toString().equals("ELECTRIC BIKE Lankeleisi; 65; 24200; FALSE; 10000; black; 2399"));
    }

}