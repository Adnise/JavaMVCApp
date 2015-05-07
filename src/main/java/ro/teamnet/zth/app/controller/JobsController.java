package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Adina Radu on 5/6/2015.
 */
@MyController(urlPath = "/location")
public class JobsController {

    @MyRequestMethod(urlPath =  "/all")
    public String getAllJobs(){
        return "allJobs";
    }

    @MyRequestMethod(urlPath =  "/one")
    public String getOneRandomJob() { return "oneRandomJob";}
}
