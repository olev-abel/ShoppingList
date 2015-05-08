package com.example.olev.shoppinglist;


import java.util.ArrayList;

public class DataProcess {
    private static DataProcess dataProcess=null;
    public static DataProcess getInstance(){
        if(dataProcess==null){
            dataProcess=new DataProcess();
        }
        return dataProcess;
    }
    public ArrayList<String> myList;
}
