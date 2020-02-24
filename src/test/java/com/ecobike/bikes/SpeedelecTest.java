package com.ecobike.bikes;

import org.junit.Test;

import static org.junit.Assert.*;


public class SpeedelecTest {
    @Test
    public void equalsTwoSpeedeleces() throws Exception {
        Bike bike1 = new Speedelec("Speedelec Lankeleisi", (short) 65, (short) 24200, false, (short) 10000, "black", 2399);
        Bike bike2 = new Speedelec("Speedelec Lankeleisi", (short) 65, (short) 24200, false, (short) 10000, "black", 2399);
        assertTrue(bike1.equals(bike2));
    }

    @Test
    public void equalsSpeedelecWithFoldingBike() {
        Bike bike1 = new Speedelec("Speedelec Lankeleisi", (short) 65, (short) 24200, false, (short) 10000, "black", 2399);
        Bike bike2 = new FoldingBike("Speedelec Lankeleisi", (short) 65, (short) 24200, (short) 10000, false, "black", 2399);
        assertFalse(bike1.equals(bike2));
    }

    @Test
    public void equalsSpeedelecWithElectricBike(){
        Bike bike1 = new Speedelec("Speedelec Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        Bike bike2 = new ElectricBike("Speedelec Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        assertFalse(bike1.equals(bike2));
    }

    @Test
    public void toStringCorrectOutput(){
        Bike bike = new Speedelec("Speedelec Lankeleisi", (short)65, (short)24200, false, (short)10000, "black", 2399);
        assertTrue(bike.toString().equals("SPEEDELEC Lankeleisi; 65; 24200; FALSE; 10000; black; 2399"));
    }
}