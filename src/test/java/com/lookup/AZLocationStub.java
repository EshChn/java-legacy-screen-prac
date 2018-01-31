package com.lookup;


import com.entity.Location;

public class AZLocationStub extends Location {

    public static String CITY_NAME = "Marana";
    public static String STATE_NAME = "AZ";

    @Override
    public String getCity() {
        return CITY_NAME;
    }

    @Override
    public String getState() {
        return STATE_NAME;
    }
}
