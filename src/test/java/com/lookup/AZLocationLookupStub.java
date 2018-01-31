package com.lookup;

import com.entity.Location;

public class AZLocationLookupStub implements LocationLookup {
    @Override
    public Location locateCityandState(String zipcode) {
        return new AZLocationStub();
    }
}
