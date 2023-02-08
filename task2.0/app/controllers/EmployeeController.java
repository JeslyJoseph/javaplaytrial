package controllers;

import config.EsConnection;
import play.mvc.Result;

import javax.inject.Inject;

import static play.mvc.Results.ok;

public class EmployeeController {
    @Inject
    private EsConnection esConnection;
    public Result connection(){
        esConnection.configure();
        return ok("Connected");
    }
}
