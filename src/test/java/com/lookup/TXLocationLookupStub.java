package com.lookup;

import com.entity.Location;

public class TXLocationLookupStub implements LocationLookup {
    @Override
    public Location locateCityandState(String zipcode) {
        return new TXLocationStub();
    }
}
