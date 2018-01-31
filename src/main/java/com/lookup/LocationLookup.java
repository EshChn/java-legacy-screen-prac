package com.lookup;

import com.entity.Location;

import java.io.IOException;
import java.net.URISyntaxException;

public interface LocationLookup {
    public Location locateCityandState(String zipcode) throws URISyntaxException, IOException;

}
