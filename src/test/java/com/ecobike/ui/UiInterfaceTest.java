package com.ecobike.ui;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class UiInterfaceTest {
    @Test
    public void getInstanceWithNull() {
        UiInterface ui = UiInterface.getInstance(null);
        assertTrue(ui != null);
    }

    @Test
    public void getInstanceTestSingleton(){
        File pathFile = new File(System.getProperty("user.dir") + "\\someDirectory\\SomeFile.txt");
        UiInterface ui = UiInterface.getInstance(pathFile.toString());
        UiInterface uiSame = UiInterface.getInstance(pathFile.toString());
        deleteFileAndParentCategory(pathFile);
        assertTrue(ui == ui);
    }

    private void deleteFileAndParentCategory(File path){
        if(path.exists()){
            path.delete();
            path.getParentFile().delete();
        }

    }
}