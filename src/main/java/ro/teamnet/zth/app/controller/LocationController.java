package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Adina Radu on 5/6/2015.
 */
@MyController(urlPath = "/locations")
public class LocationController {

@MyRequestMethod(urlPath = "/all")
    public String getAllLocations(){
        return "allLocations";
    }

    @MyRequestMethod(urlPath =  "/one")
    public String getOneRandomLocation() { return "oneRandomLocation";}
}
