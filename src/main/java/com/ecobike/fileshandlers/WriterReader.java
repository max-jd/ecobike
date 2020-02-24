package com.ecobike.fileshandlers;

import com.ecobike.bikes.Bike;
import com.ecobike.bikes.ElectricBike;
import com.ecobike.bikes.FoldingBike;
import com.ecobike.bikes.Speedelec;

import java.io.*;
import java.util.*;


public class WriterReader {
    //need for know if new bikes was added to the list
    private int previousReadBikes;
    //need for know how many bikes was added to the list
    private int previousWrittenBikes;

    private File rwFile;

    public List<Bike> getBikesFromFile(File fileFrom) {
       if(!fileFrom.exists()){
           System.out.println("The file doesn't exist. Please, specify the path correctly.");
           return null;
       }

       List<Bike> listOfBikes = new ArrayList<Bike>();

       try(FileReader fileReader = new FileReader(fileFrom);
       BufferedReader bufferedReader = new BufferedReader(fileReader)) {
           String textBike = null;

           while((textBike = bufferedReader.readLine()) != null) {
               String pattern = "; ";

               if(textBike.startsWith("FOLDING")) {
                   //7 tokens == 7 parameters
                   String parameters[] = textBike.split(pattern);
                   listOfBikes.add(new FoldingBike(parameters[0], Short.parseShort(parameters[1]),
                           Short.parseShort(parameters[2]), Short.parseShort(parameters[3]),
                           Boolean.parseBoolean(parameters[4]), parameters[5],
                           Integer.parseInt(parameters[6])));
               } else if(textBike.startsWith("E-BIKE")) {
                   //7 tokens == 7 parameters
                   String parameters[] = textBike.split(pattern);
                   listOfBikes.add(new ElectricBike(parameters[0], Short.parseShort(parameters[1]),
                           Short.parseShort(parameters[2]), Boolean.parseBoolean(parameters[3]),
                           Short.parseShort(parameters[4]), parameters[5],
                           Integer.parseInt(parameters[6])));
               }
               else if(textBike.startsWith("SPEEDELEC")) {
                   //7 tokens == 7 parameters
                   String parameters[] = textBike.split(pattern);
                   listOfBikes.add(new Speedelec(parameters[0], Short.parseShort(parameters[1]),
                           Short.parseShort(parameters[2]), Boolean.parseBoolean(parameters[3]),
                           Short.parseShort(parameters[4]), parameters[5],
                           Integer.parseInt(parameters[6])));
               }else{
                   System.out.println(
                           "Error. The file format is corrupt! The unknown type is found. " +
                           "Please, check the file and fix it, then run the program again.");
                   System.exit(1);
               }
           }
       }catch(IOException ex) {
           System.out.println("Error. IOException. Please, try again.");
           return null;
       }

       //fix the number of Bikes that we read
       previousReadBikes = listOfBikes.size();
       return listOfBikes;
   }

   public boolean putBikesToFile(List<Bike> bikes) {
       try(FileWriter fileWriter = new FileWriter(rwFile);
       BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);){
           String textBike = null;
           for(Bike bike : bikes){
               textBike = bike.toString();
               bufferedWriter.write(textBike + "\n");
               //auto-flushing because of try-with-resources
           }
       }catch(IOException ex){
           System.out.println("Error. IOException. Something went wrong. Please, try again." + ex);
           return false;
       }

       //fix how many bikes were written
       previousWrittenBikes = bikes.size();
       return true;
   }

    public boolean setFilePath(String filePath){
       File file = new File(filePath);
       if(file.isDirectory()){
            System.out.println("It is a Directory. Need specify a path for a file.");
            return false;
       } else if(! (file.toString().endsWith(".txt"))){
            System.out.println("Error. Extension a file must be txt. Please, be attention when writing a path of the file. ");
            return false;
       } else if(! (file.getParentFile().exists())) {
            System.out.println("The path and file do not exist. Creating the new folder(s) and new file...");
            file.getParentFile().mkdirs();
            try{
                file.createNewFile();
            }catch(IOException ex){
                System.out.println("The file cannot be created." + ex);
                return false;
            }
        }

        rwFile = file;
        return true;
    }

    public File getRwFile() {
        return rwFile;
    }

    public int getPreviousReadBikes() {
        return previousReadBikes;
    }

    public int getPreviousWrittenBikes() {
        return previousWrittenBikes;
    }

}
