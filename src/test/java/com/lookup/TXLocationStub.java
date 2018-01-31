package com.lookup;


import com.entity.Location;

public class TXLocationStub extends Location {

    public static String CITY_NAME = "Addison";
    public static String STATE_NAME = "TX";

    @Override
    public String getCity() {
        return CITY_NAME;
    }

    @Override
    public String getState() {
        return STATE_NAME;
    }
}
