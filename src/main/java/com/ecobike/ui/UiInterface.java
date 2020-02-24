package com.ecobike.ui;

import com.ecobike.bikes.Bike;
import com.ecobike.bikes.ElectricBike;
import com.ecobike.bikes.FoldingBike;
import com.ecobike.bikes.Speedelec;
import com.ecobike.fileshandlers.WriterReader;
import com.ecobike.ui.util.GeneratorNiceOutput;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

//Singleton
public class UiInterface {
    private static String[] textsMenu = new String[7];
    private List<Bike> bikes;
    private static volatile UiInterface instance;
    private WriterReader  writerReader = new WriterReader();
    private Scanner scanner = new Scanner(System.in);

    private FutureTask sorting;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private UiInterface(String bikesFilePath){
        textsMenu[0] = "1 - Show the entire EcoBike catalog";
        textsMenu[1] = "2 - Add a new folding bike";
        textsMenu[2] = "3 - Add a new speedelec";
        textsMenu[3] = "4 - Add a new e-bike";
        textsMenu[4] = "5 - Find the first item of a particular brand";
        textsMenu[5] = "6 - Write to file";
        textsMenu[6] = "7 - Stop the program";
;
        //if we got path to file - set it, and read bikes
        if(bikesFilePath != null){
            if(writerReader.setFilePath(bikesFilePath));
                readFromFile(new File(bikesFilePath));
        }
    }

    public void run(){
        while(true){
            //if wasn't got through a console input a path of a file  - request from a user, after will show menu
            if (writerReader.getRwFile() == null) {
                    requestPath();
            }
            showMenu();
            //get input and handling it
            if(!scanner.hasNextInt()) {
                System.out.println("It is not an integer value.");
                continue;
            }else {
               int choice = scanner.nextInt();
               //consume '\n'
                scanner.nextLine();
                if(choice == 1) {
                    showAllCatalog();
                }
                else if(choice == 2 || choice == 3 || choice == 4) {
                   try{
                        Bike createdBike = createBike(choice);
                        if(createdBike != null)
                            addNewBike(createdBike);
                   }catch(IOException ex){
                       System.out.println("Error. The bike wasn't created.");
                   }
               }else if(choice == 5){
                    subMenuSearchByBrand:
                    do{
                        System.out.println("Do you want search by brand or by specific bike? " +
                            "1 (by brand), 2(by specific), 0(to return)");
                        if(!scanner.hasNextLine()){
                            System.out.println("It is not a valid input.");
                            continue;
                        }
                        String criteria = scanner.nextLine();
                        if(criteria.equals("1")){
                            System.out.println("Enter the brand:");
                            String searchedBrand = scanner.nextLine();
                            Bike searchedBike = find(searchedBrand);
                            if(searchedBike != null){
                                System.out.println("The First bike of this brand is: " + searchedBike + System.lineSeparator());
                            }else{
                                System.out.println("Brand wasn't found." + System.lineSeparator());
                                continue subMenuSearchByBrand;
                            }
                        }else if(criteria.equals("2")){
                            do{
                                System.out.println("Please, choose the type of the bike for the search - " +
                                        "2(folding bike), 3(speedelec), 4(e-bike) or 0(to return):");
                                String searchedType = scanner.nextLine();
                                if(searchedType.equals("2")){
                                    try{
                                     Bike createdBike = createBike(Integer.parseInt(searchedType));
                                     int resultIndex = find(createdBike);
                                     if(resultIndex >= 0){
                                        System.out.println("The bike was found: " + bikes.get(resultIndex) + System.lineSeparator());
                                     }else {
                                         System.out.println("The bike with the specified parameters wasn't found." + System.lineSeparator());
                                     }
                                    }catch(IOException ex){
                                        System.out.println("Error. " + ex.toString());
                                    }
                                }else if(searchedType.equals("3")){
                                    try{
                                        Bike createdBike = createBike(Integer.parseInt(searchedType));
                                        int resultIndex = find(createdBike);
                                        if(resultIndex >= 0){
                                            System.out.println("The bike was found: " + bikes.get(resultIndex) + System.lineSeparator());
                                        }else {
                                            System.out.println("The bike with the specified parameters wasn't found." + System.lineSeparator());
                                        }
                                    }catch(IOException ex){
                                        System.out.println("Error. " + ex.toString());
                                    }
                                }else if(searchedType.equals("4")) {
                                    try{
                                        Bike createdBike = createBike(Integer.parseInt(searchedType));
                                        int resultIndex = find(createdBike);
                                        if(resultIndex >= 0) {
                                            System.out.println("The bike was found: " + bikes.get(resultIndex));
                                        }else {
                                            System.out.println("The bike with the specified parameters wasn't found." + System.lineSeparator());
                                        }
                                    }catch(IOException ex) {
                                        System.out.println("Error. " + ex.toString());
                                    }
                                }else if(searchedType.equals("0")) {
                                    //continue from the outer cycle
                                    continue subMenuSearchByBrand;
                                }  else {
                                    System.out.println("Wrong input");
                                }
                            }while(true);
                        }else if(criteria.equals("0")){
                            //exit from the inner cycle
                            break;
                        }else{
                            System.out.println("Wrong input.");
                        }
                    } while(true);
                }else if(choice == 6){
                   //if a user didn't add some bikes
                   if(bikes.size() == writerReader.getPreviousReadBikes()) {
                       System.out.println("Nothing to write. Add some bikes.");
                   }else{
                       writeToFile();
                       System.out.println("New bikes were written to the file.");
                   }
                }else if(choice == 7){
                    stropProgram();
                }
            }
            System.out.println();
        }
    }

    /** Caution: ugly code :)
     * @param i is the choice of user (2 to 4)
     * @throws IOException - if this method gets i < 2 || i > 4
     */
    private Bike createBike(int i) throws IOException{
        switch (i){
            case 2:{
                FoldingBike tempBike = new FoldingBike();
                // We get every field for a new bike, and don't let go a user until he/she put all ones.
                while(true){
                    if(tempBike.getType() == null){
                        System.out.println("Enter the fully type:");
                        if(! scanner.hasNextLine()){
                            System.out.println("It is not a valid input.");
                            continue;
                        }
                        String type = scanner.nextLine();
                        tempBike.setType(type);
                    }
                    if(tempBike.getBrand() == null){
                        System.out.println("Enter the brand:");
                        if(!scanner.hasNextLine()){
                            System.out.println("It is not a valid input.");
                            continue;
                        }
                        String brand = scanner.nextLine();
                        tempBike.setBrand(brand);
                    }

                    //0 - the valid input - if weight for now is unknown
                    if(tempBike.getSizeWheels() == -1){
                        System.out.println("Enter the size of the wheels (in inch):");
                        if(! scanner.hasNextShort()){
                            System.out.println("It is not an integer number.");
                            //discard garbage
                            scanner.nextLine();
                            continue;
                        }
                        short sizeOfWheels = scanner.nextShort();
                        //consume '\n'
                        scanner.nextLine();
                        if(sizeOfWheels < 0){
                            System.out.println("The value cannot be negative.");
                            continue;
                        }
                        else{
                            tempBike.setSizeWheels(sizeOfWheels);
                        }
                    }

                    if(tempBike.getNumberGears() == -1){
                        System.out.println("Enter the number of gears:");
                        if(! scanner.hasNextShort()){
                            System.out.println("It is not an integer number.");
                            //discard garbage
                            scanner.nextLine();
                            continue;
                        }
                        short numberOfGears = scanner.nextShort();
                        //consume '\n'
                        scanner.nextLine();
                        if(numberOfGears < 0){
                            System.out.println("The value cannot be negative.");
                            continue;
                        }
                        else{
                            tempBike.setNumberGears(numberOfGears);
                        }
                    }

                    //0 - the valid input - if weight for now is unknown
                    if(tempBike.getWeight() == -1){
                        System.out.println("Enter the weight of the bike (in grams):");
                        if(! scanner.hasNextShort()){
                            System.out.println("It is not an integer number.");
                            //discard garbage
                            scanner.nextLine();
                            continue;
                        }
                        short weight = scanner.nextShort();
                        //consume '\n'
                        scanner.nextLine();
                        if(weight < 0){
                            System.out.println("The value cannot be negative.");
                            continue;
                        }
                        else{
                            tempBike.setWeight(weight);
                        }
                    }

                    if(tempBike.isFrontBackLights() == null){
                        System.out.println("Enter the availability of lights at front and back (TRUE/FALSE):");
                        if(! scanner.hasNextBoolean()){
                            System.out.println("It is not a boolean value.");
                            //discard garbage
                            scanner.nextLine();
                            continue;
                        }
                        Boolean lights = scanner.nextBoolean();
                        //consume '\n'
                        scanner.nextLine();
                        tempBike.setFrontBackLights(lights);
                        }

                    if(tempBike.getColor() == null){
                        System.out.println("Enter the color:");
                        if(!scanner.hasNextLine()){
                            System.out.println("It is not a valid input.");
                        }
                        String color = scanner.nextLine();
                        tempBike.setColor(color);
                    }

                    if(tempBike.getPrice() == -1){
                        System.out.println("Enter the price:");
                        if(! scanner.hasNextInt()){
                            System.out.println("It is not an integer number.");
                            //discard garbage
                            scanner.nextLine();
                            continue;
                        }
                        int price = scanner.nextInt();
                        //0 if price for now not is known
                        if(price > 0){
                            tempBike.setPrice(price);
                            //consume '\n'
                            scanner.nextLine();
                            break;
                        }
                        else {
                            System.out.println("The value cannot be negative.");
                            continue;
                        }
                    }
                }
                return tempBike;
            }
            //for do not duplicate the code
            case 3:
            case 4:{
                    ElectricBike tempBike = (i == 3) ? (new Speedelec()) : (new ElectricBike());
                    while(true){
                        if(tempBike.getType() == null){
                            System.out.println("Enter the fully type:");
                            if(!scanner.hasNextLine()){
                                System.out.println("It is not a valid input.");
                                continue;
                            }
                            String type = scanner.nextLine();
                            tempBike.setType(type);
                        }
                        if(tempBike.getBrand() == null){
                            System.out.println("Enter the brand:");
                            if(!scanner.hasNextLine()){
                                System.out.println("It is not a valid input.");
                                continue;
                            }
                            String brand = scanner.nextLine();
                            tempBike.setBrand(brand);
                        }
                        if(tempBike.getMaxSpeed() == -1){
                            System.out.println("Enter the maximum speed (in km/h):");
                            if(! scanner.hasNextShort()){
                                System.out.println("It is not an integer number.");
                                //discard garbage
                                scanner.nextLine();
                                continue;
                            }
                            short maxSpeed = scanner.nextShort();
                            //consume '\n'
                            scanner.nextLine();
                            //0 if maximum speed yet unknown
                            if(maxSpeed < 0){
                                System.out.println("The value cannot be negative.");
                                continue;
                            }
                            else{
                                tempBike.setMaxSpeed(maxSpeed);
                            }
                        }
                        if(tempBike.getWeight() == -1){
                            System.out.println("Enter the weight of the bike (in grams):");
                            if(! scanner.hasNextShort()){
                                System.out.println("It is not an integer number.");
                                //discard garbage
                                scanner.nextLine();
                                continue;
                            }
                            short weight = scanner.nextShort();
                            //consume '\n'
                            scanner.nextLine();
                            if(weight < 0){
                                System.out.println("The value cannot be negative.");
                                continue;
                            } else{
                                tempBike.setWeight(weight);
                            }
                        }

                        if(tempBike.isFrontBackLights() == null){
                            System.out.println("Enter the availability of lights at front and back (TRUE/FALSE):");
                            if(! scanner.hasNextBoolean()){
                                System.out.println("It is not a boolean value.");
                                //discard garbage
                                scanner.nextLine();
                                continue;
                            }
                            Boolean lights = scanner.nextBoolean();
                            //consume '\n'
                            scanner.nextLine();
                            tempBike.setFrontBackLights(lights);
                        }
                        if(tempBike.getBatteryCapacity() == -1){
                            System.out.println("Enter the battery capacity (in mAh):");
                            if(! scanner.hasNextShort()){
                                System.out.println("It is not an integer number.");
                                //discard garbage
                                scanner.nextLine();
                                continue;
                            }
                            short capacity = scanner.nextShort();
                            //consume '\n'
                            scanner.nextLine();
                            if(capacity < 0){
                                System.out.println("The value cannot be negative.");
                                continue;
                            }
                            tempBike.setBatteryCapacity(capacity);
                        }
                        if(tempBike.getColor() == null){
                            System.out.println("Enter the color:");
                            if(!scanner.hasNextLine()){
                                System.out.println("It is not a valid input.");
                                continue;
                            }
                            String color = scanner.nextLine();
                            tempBike.setColor(color);
                        }
                        if(tempBike.getPrice() == -1){
                            System.out.println("Enter the price:");
                            if(! scanner.hasNextInt()){
                                System.out.println("It is not an integer number.");
                                //discard garbage
                                scanner.nextLine();
                                continue;
                            }
                            int price = scanner.nextInt();
                            //consume '\n'
                            scanner.nextLine();
                            //0 if price for now not is known
                            if(price > 0){
                                tempBike.setPrice(price);
                                break;
                            }
                            else {
                                System.out.println("The value cannot be negative.");
                                continue;
                            }
                        }

                    }
                    return tempBike;
            }
            default:{
                throw new IOException("The value mast be 2 to 4.");
            }

        }
    }

   public static UiInterface getInstance(String bikesFilePath){
        //double-check
        if (instance == null){
            synchronized(UiInterface.class){
                if(instance == null)
            instance = new UiInterface(bikesFilePath);
            }
        }
        return instance;
    }

    private Set<String> showAllCatalog(){
       sortByPrice();
       isCallableDone();

       //The mark if user want continue or break output of bikes
        continueOrBreakFromOuterCycle:
        //here i = 1 because of 0 % 10 == 0
        for(int i = 1; i <= bikes.size(); i++){
           System.out.println(GeneratorNiceOutput.generateBike(bikes.get(i-1)));
           //showing 10 bikes on one page
           if((i % 10) == 0){
               //Don't let go a user until he enters the valid value
               while(true){
                   System.out.println(System.lineSeparator() + "Show more? 1(yes) 0(no)");
                   if(!scanner.hasNextLine()){
                       System.out.println("It is not a valid input.");
                   }
                   String choice = scanner.nextLine();
                   if(choice.equals("1"))
                       continue continueOrBreakFromOuterCycle;
                   else if(choice.equals("0"))
                       break continueOrBreakFromOuterCycle;
                   System.out.println("Please, enter 1(yes) 0(no).");
               }
           }

       }
       return null;
   }

    private void addNewBike(Bike newBike){
        bikes.add(newBike);
        System.out.println("The bike was added.");
    }

    private Bike find(String... searchedParameters) throws IllegalArgumentException {
        if(searchedParameters.length == 1){
            //sort and waite if necessary
            sortByBrands();
            isCallableDone();
            
            String brand = searchedParameters[0];
            //create dummy-bike just for search by brand
            Bike bikeForSearch = new FoldingBike();
            bikeForSearch.setBrand(brand);
            int indexSearchedBrand = Collections.binarySearch(bikes, bikeForSearch, (Bike brandBike, Bike secondBike) ->
                    brandBike.getBrand().compareTo(secondBike.getBrand()));
            if(indexSearchedBrand >= 0) {
                return bikes.get(indexSearchedBrand);
            }else {
                return null;
            }
        }else{
            throw new IllegalArgumentException("Wrong number of the arguments");
        }
    }

    private void isCallableDone() {
        if(! sorting.isDone())
            System.out.println("Please, wait. It can take a while...");
        //Don't let go a user to continue, until sorting will be ended
        while(! sorting.isDone()){
            try{
                Thread.sleep(100);
            }catch(InterruptedException ex) {
                System.out.print("Error." + ex);
            }
            System.out.print(".");
        }
        System.out.println(System.lineSeparator());
    }

    private void sortByBrands() {
        sorting = new FutureTask<Boolean>( () -> {
            synchronized (bikes){
                //sorting by brands of the bikes
                Collections.sort(bikes, (a,b) -> a.getBrand().compareTo(b.getBrand()));
                bikes.notifyAll();
            }
            return true;
        });
        executor.submit(sorting);
    }

    private void sortByFullNameBikes(){
        sorting = new FutureTask<Boolean>( () -> {
            synchronized (bikes){
                //sorting by full name of bikes
                Collections.sort(bikes);
                bikes.notifyAll();
            }
            return true;
        });
        executor.submit(sorting);
    }

    private void sortByPrice(){
        sorting = new FutureTask<Boolean>(() -> {{
            //sorting by an ascending order - according to the specification
            synchronized (bikes){
                Collections.sort(bikes, (bike1,bike2) -> bike2.getPrice() - bike1.getPrice());
                bikes.notifyAll();
            }
            return true;
        }});
        executor.submit(sorting);
    }

    private int find(Bike concreteBike) {
        //sort and waite if necessary
        sortByFullNameBikes();
        isCallableDone();

        return Collections.binarySearch(bikes, concreteBike);
    }
 
    private void writeToFile(){
        writerReader.putBikesToFile(bikes);
    }

    private void stropProgram(){
        //size of the list of bikes at the start of the program equals to previous read bikes (if it is not - new bikes were added) &&
        //size of the list of bikes must be not equal to previous written the bikes (if so - a user already pressed the 6-th item from the menu).
        if (bikes.size() > writerReader.getPreviousReadBikes() && !(bikes.size() == writerReader.getPreviousWrittenBikes())) {
            String choice = null;
            while(true){
                System.out.println("New bikes were added. Do you want to save it? Press 1(yes), 0(no) or r(to return).");
                if(! scanner.hasNextLine()){
                    System.out.println("Wrong input.");
                    continue;
                }
                choice = scanner.nextLine();
                if (choice.equals("1")) {
                    writeToFile();
                    System.out.println("New bikes were saved." + System.lineSeparator() + "The program is stopped.");
                    System.exit(0);
                }else if (choice.equals("0")) {
                    System.out.println("The program is stopped.");
                    System.exit(0);
                }else if(choice.equals("r")) {
                    return;
                }
            }
        }else {
            System.out.println("The program is stopped.");
            System.exit(0);
        }
    }

    private void requestPath(){
        System.out.println("Oops! The file for loading from is not found. Add new one (if it doesn't exist it will be created).");
        boolean creatingResult = false;
        while(true){
            String path = scanner.nextLine();
            //true if the file was set
            creatingResult = writerReader.setFilePath(path);
            if(creatingResult) {
                System.out.println("Creating was successful.");
                //populating the List of bikes with data
                readFromFile(writerReader.getRwFile());
                return;
            }else{
                System.out.println("Creating wasn't successful. Be more attention and try again.");
            }
        }
    }

    private void showMenu(){
        //just 7 strings + 7 lineSeparators
        System.out.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n", textsMenu[0],textsMenu[1],textsMenu[2],
                textsMenu[3],textsMenu[4], textsMenu[5], textsMenu[6]
        );
    }

    private boolean readFromFile(File file){
       bikes = writerReader.getBikesFromFile(file);
       if(bikes != null)
           return true;
       return false;
    }
}
