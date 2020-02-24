package com.ecobike.bikes;

import org.junit.Test;

import static org.junit.Assert.*;


public class FoldingBikeTest {
    @Test
    public void equalsTwoFoldingBikes(){
        Bike bike1 = new FoldingBike("Folding Bike Lankeleisi", (short)65, (short)24200, (short)10000,  false, "black", 2399);
        Bike bike2 = new FoldingBike("Folding Bike Lankeleisi", (short)65, (short)24200, (short)10000,  false, "black", 2399);
        assertTrue(bike1.equals(bike2));
    }

    @Test
    public void equalsFoldingBikeWithElectricBike(){
        Bike bike1 = new FoldingBike("Folding Bike Lankeleisi", (short)65, (short)24200, (short)10000,  false, "black", 2399);
        Bike bike2 = new ElectricBike("Folding Bike Lankeleisi", (short) 65, (short) 24200, false, (short) 10000, "black", 2399);
        assertFalse(bike1.equals(bike2));
    }

    @Test
    public void equalsFoldingBikeWithSpeedelec(){
        Bike bike1 = new FoldingBike("Folding Bike Lankeleisi", (short)65, (short)24200, (short)10000,  false, "black", 2399);
        Bike bike2 = new Speedelec("Folding Bike Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        assertFalse(bike1.equals(bike2));
    }

    @Test
    public void toStringCorrectOutput(){
        Bike bike = new FoldingBike("Folding Bike Lankeleisi", (short)65, (short)24200, (short)10000,  false, "black", 2399);
        assertTrue(bike.toString().equals("FOLDING BIKE Lankeleisi; 65; 24200; 10000; FALSE; black; 2399"));
    }

}