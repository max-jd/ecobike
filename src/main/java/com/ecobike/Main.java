package com.ecobike;

import com.ecobike.ui.UiInterface;

public class Main {
    public static void main(String[] args){
        UiInterface ui;
        //was a file path specified through command line?
        if(args.length != 0){
            ui = UiInterface.getInstance(args[0]);
        }
        else{
            ui = UiInterface.getInstance(null);
        }
        ui.run();
    }
}
