package com.ecobike.fileshandlers;

import com.ecobike.bikes.Bike;
import com.ecobike.bikes.ElectricBike;
import com.ecobike.bikes.FoldingBike;
import com.ecobike.bikes.Speedelec;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



import static org.junit.Assert.*;

public class WriterReaderTest {
    WriterReader writerReader = new WriterReader();

    @Test(expected = NullPointerException.class)
    public void setFilePathNull(){
        writerReader.setFilePath(null);
    }

    @Test
    public void setFilePathNotTxt(){
        boolean result = writerReader.setFilePath(System.getProperty("user.dir") + "\\someDirectory\\someFile.java");
        assertFalse(result);
    }

    @Test
    public void setFilePathCreatingNotExisting() throws IOException {
        String pathToFile = System.getProperty("user.dir")+ File.separator
                + "someDirectory1" + File.separator + "someDirectory2" + File.separator + "someFile.txt";

        writerReader.setFilePath(pathToFile);

        File pathForDeleting = writerReader.getRwFile();
        assertTrue(pathForDeleting.exists());

        //sweeping up
        deleteFileWithDirectories(pathForDeleting);
    }

    @Test
    public void setFilePathCreateNotExistingWrongInput() throws IOException {
        String pathToFile = System.getProperty("user.dir")+ System.getProperty("file.separator")
                + "someDirectory1" + System.getProperty("file.separator") + "someDirectory2";

        boolean result = writerReader.setFilePath(pathToFile);

        assertFalse("Method returned true - should false", result);
    }

    @Test
    public void getBikesFromFileDoesNotExist() throws Exception {
        String pathToFile = System.getProperty("user.dir")+ File.separator
                + "someDirectory1" + File.separator + "someDirectory2" + File.separator + "someFile.txt";
        File pathToNotExistingFile = new File(pathToFile);
        Object nullOb = writerReader.getBikesFromFile(pathToNotExistingFile);
        assertNull(nullOb);
    }

    @Test
    public void writeToAndReadFromFileBikes() throws IOException {
        String pathToFile = System.getProperty("user.dir") + File.separator
                + "someOneDirectory" + File.separator + "someSecondDirectory" + File.separator + "SomeTxtFile.txt";

        assertTrue("The file for the test cannot be read.",
                writerReader.setFilePath(pathToFile));

        List<Bike> originalListBikes = createSomeBikes();
        writerReader.putBikesToFile(originalListBikes);

        List<Bike> outputListBikes = writerReader.getBikesFromFile(writerReader.getRwFile());

        //do the two asserts: the sizes equal, and the original list have all bikes from output one
        assertTrue( "The sizes is different", outputListBikes.size()== originalListBikes.size());
        assertTrue("The original list have not all bikes from output one",
                originalListBikes.containsAll(outputListBikes));

        //sweeping up
        deleteFileWithDirectories(writerReader.getRwFile());
    }

    private List<Bike> createSomeBikes() {
        Bike eBike = new ElectricBike("E-BIKE Goodyear", (short)50, (short)15, true, (short) 3600, "Black", 789);
        Bike foldingBike = new FoldingBike("FOLDING BIKE GoodMorn", (short)15, (short)7, (short) 15, false, "White", 777);
        Bike speedelec = new Speedelec("SPEEDELEC GoodDay", (short)75, (short)25, true, (short) 5600, "Yellow", 1500 );

        List<Bike> bikes = new LinkedList<Bike>();
        bikes.add(eBike);
        bikes.add(foldingBike);
        bikes.add(speedelec);

        return bikes;
    }

    private void deleteDirectories(File... pathsForDeleting) throws IOException {
        for(File path : pathsForDeleting){
            path.delete();
        }
    }

    private void deleteFileWithDirectories(File file) throws IOException {
        if(file.exists() && file.isFile()){
            file.delete();
            deleteDirectories(file.getParentFile(), file.getParentFile().getParentFile());
            return;
        }else{
            throw new IllegalArgumentException("The path for deleting the file is wrong.");
        }
    }
}